package Lab2;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Map;
import java.util.Properties;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

import scala.Tuple2;


import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.api.java.function.VoidFunction2;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka09.ConsumerStrategies;
import org.apache.spark.streaming.kafka09.KafkaUtils;
import org.apache.spark.streaming.kafka09.LocationStrategies;



/**
 * @author jcasaletto
 * 
 * Consumes messages from input Kafka topic, calculates averages, then outputs averages to output Kafka topic
 *
 * Usage: StockSparkApp <broker> <master> <in-topic> <out-topic> <cg>
 *   <broker> is one of the servers in the kafka cluster
 *   <master> is either local[n] or yarn-client
 *   <in-topic> is the kafka topic to consume from
 *   <out-topic> is the kafka topic to produce to
 *   <cg> is the consumer group name
 *   <interval> is the number of milliseconds per batch
 *
 */

public final class StockSparkApp {

    private static boolean verbose = true;
    public static void main(String[] args) {
        if (args.length < 6) {
            System.err.println("Usage: StockSparkApp <broker> <master> <in-topic> <out-topic> <cg> <interval>");
            System.err.println("eg: StockSparkApp localhost:9092 localhost:2181 test out mycg 5000");
            System.exit(1);
        }

        // set variables from command-line arguments
        final String broker = args[0];
        String master = args[1];
        String inTopic = args[2];
        final String outTopic = args[3];
        String consumerGroup = args[4];
        long interval = Long.parseLong(args[5]);
        
        // define topic to subscribe to
        final Pattern topicPattern = Pattern.compile(inTopic, Pattern.CASE_INSENSITIVE);
    
        // set Kafka client parameters
        Map<String, Object> kafkaParams = new HashMap<String, Object>();
        kafkaParams.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaParams.put("value.deserializer", "org.apache.kafka.connect.json.JsonDeserializer");
        kafkaParams.put("bootstrap.servers", broker);
        kafkaParams.put("group.id", consumerGroup);
        kafkaParams.put("enable.auto.commit", true);

        // initialize the streaming context
        JavaStreamingContext jssc = new JavaStreamingContext(master, "StockSparkApp", new Duration(interval));

        // pull ConsumerRecords out of the stream
        final JavaInputDStream<ConsumerRecord<String, JsonNode>> messages = 
                        KafkaUtils.createDirectStream(
                        jssc,
                        LocationStrategies.PreferConsistent(),
                        ConsumerStrategies.<String, JsonNode>SubscribePattern(topicPattern, kafkaParams)
                      );
    
        // pull values out of ConsumerRecords 
        JavaPairDStream<String, JsonNode> keyValuePairs = messages.mapToPair(new PairFunction<ConsumerRecord<String, JsonNode>, String, JsonNode>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Tuple2<String, JsonNode> call(ConsumerRecord<String, JsonNode> record) throws Exception {
                // replace 'null' with key-value pair as tuple2
                return new Tuple2<String, JsonNode>(record.key(), record.value());
            }
        });
        
        keyValuePairs.foreachRDD(new VoidFunction<JavaPairRDD<String,JsonNode>>() {
            private static final long serialVersionUID = 1L;
            @Override
            public void call(JavaPairRDD<String, JsonNode> rdd) throws Exception {
                final long count = rdd.count();
                if (verbose) System.out.println("count: "+count);
                rdd.foreachPartition(new VoidFunction<Iterator<Tuple2<String, JsonNode>>>() {
                    private static final long serialVersionUID = 1L;
                    @Override
                    public void call(Iterator<Tuple2<String, JsonNode>> recordIterator) throws Exception {
                        double sumHigh = 0, sumLow = 0, sumOpen = 0, sumClose = 0, lastClose=0;
                        long sumVolume=0;
                        String stockSymbol = null, lastTimestamp = null;
                        JsonNode node;
                        Tuple2<String, JsonNode> tuple;
                        while(recordIterator.hasNext()) {
                            // get next record
                            tuple = recordIterator.next();
                            
                            // pull out timestamp, stockSymbol from record
                            stockSymbol = tuple._1();
                            node = tuple._2();
                            lastTimestamp = node.get("timestamp").asText();
                            
                            // pull out metrics from record
                            lastClose = node.get("close").asDouble();
                            
                            // calculate sums (sumHigh += ... , sumLow += ..., etc)
                            sumHigh += node.get("high").asDouble();
                            sumLow += node.get("low").asDouble();
                            sumOpen += node.get("open").asDouble();
                            sumClose += lastClose;
                            sumVolume += node.get("volume").asLong();

                        }
                        
                        // calculate meanHigh, meanLow, ...
                        double meanHigh = sumHigh / count, meanLow = sumLow / count, meanOpen = sumOpen / count, meanClose = sumClose / count, meanVolume = sumVolume / (count + 0.0);

                        // configure Kafka producer props
                        Properties producerProps = new Properties();
                        producerProps.put("bootstrap.servers", broker);
                        producerProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
                        producerProps.put("node.serializer", "org.apache.kafka.connect.json.JsonSerializer");
                        
                        // create new ObjectNode to put data in
                        ObjectNode value = JsonNodeFactory.instance.objectNode();
                        
                        // put key-node pairs in ObjectNode
                        value.put("lastTimestamp", lastTimestamp);
                        value.put("meanHigh", meanHigh);
                        value.put("meanLow", meanLow);
                        value.put("meanOpen", meanOpen);
                        value.put("meanClose", meanClose);
                        value.put("meanVolume", meanVolume);
                        value.put("lastClose", lastClose);

                        if (verbose) System.out.println(value.toString());

                        // create a properly-parameterized ProducerRecord
                        ProducerRecord<String, JsonNode> record = new ProducerRecord<String, JsonNode>(outTopic, stockSymbol, value);

                        // instantiate the KafkaProducer
                        KafkaProducer<String, JsonNode> producer = configureProducer(broker);
                        
                        // send the producer record
                        producer.send(record);

                        // close the producer
                        producer.close();
                    }                     
                });
            }           
        });

        // start the consumer
        jssc.start();
    
        // stay in infinite loop until terminated
        try {
            jssc.awaitTermination();
        } 
        catch (InterruptedException e) {
        }
    }

    public static KafkaProducer<String, JsonNode> configureProducer(String brokerSocket) {
        Properties props = new Properties();
        props.put("value.serializer", "org.apache.kafka.connect.json.JsonSerializer");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("bootstrap.servers", brokerSocket);
        return new KafkaProducer<String, JsonNode>(props);
    }
}

