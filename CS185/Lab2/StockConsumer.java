package Lab2;

import org.apache.kafka.clients.consumer.*;


import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class StockConsumer {

    private static boolean verbose = true;
    // Declare a new consumer
    public static KafkaConsumer<String, JsonNode> consumer;

    public static void main(String[] args) throws IOException {
        // check command-line arguments
        if (args.length != 5) {
            System.err.println("usage: StockConsumer <broker-socket> <input-topic> <stock-symbol> <group-id> <threshold-%>");
            System.err.println("e.g.: StockConsumer localhost:9092 stats orcl mycg 0.5");
            System.exit(1);
        }

        // initialize varaibles
        String brokerSocket = args[0];
        String inputTopic = args[1];
        String stockSymbol = args[2];
        String groupId = args[3];
        double thresholdPercentage = Double.parseDouble(args[4]);

        long pollTimeOut = 1000;


        // configure consumer
        configureConsumer(brokerSocket, groupId);

        // subscribe to the topic
        consumer.subscribe(Arrays.asList(inputTopic));

        // loop infinitely -- pulling messages out every pollTimeOut ms
        double currentAggregatedStatistic, previousAggregatedStatistic = 0, delta = 0;
        double lastClose = 0;
        String lastTimestamp = null, decision = null;

        while (true) {
            ConsumerRecords<String, JsonNode> records = consumer.poll(pollTimeOut);
            long count = records.count();
            if (verbose) System.out.println("count: " + count);
            if (count == 0) continue;
            Iterator<ConsumerRecord<String, JsonNode>> iterator = records.iterator();

            // iterate through message batch
            double sumHigh = 0, sumLow = 0, sumOpen = 0, sumClose = 0, sumVolume = 0;

            while (iterator.hasNext()) {

                // create a ConsumerRecord from message
                ConsumerRecord<String, JsonNode> record = iterator.next();

                // pull out statistics from message
                JsonNode value = record.value();
                //if (verbose) System.out.println(value.toString());
                lastClose = value.get("lastClose").asDouble();
                lastTimestamp = value.get("lastTimestamp").asText();

                sumHigh += value.get("meanHigh").asDouble();
                sumLow += value.get("meanLow").asDouble();
                sumOpen += value.get("meanOpen").asDouble();
                sumClose += lastClose;
                sumVolume += value.get("meanVolume").asDouble();
            }

            // calculate batch statistics meanHigh, meanLow, meanOpen, meanClose, meanVolume
            double meanHigh = sumHigh / count, meanLow = sumLow / count, meanOpen = sumOpen / count, meanClose = sumClose / count, meanVolume = sumVolume / count;
            System.out.printf("high: %f, low: %f, open: %f, close: %f, volume: %f\n", meanHigh, meanLow, meanOpen, meanClose, meanVolume);

            // calculate currentAggregatedStatistic and compare to previousAggregatedStatistic
            currentAggregatedStatistic = meanVolume * (meanHigh + meanLow + meanOpen + meanClose) / 4.0;
            if (verbose)
                System.out.println("current" + currentAggregatedStatistic + ", previous" + previousAggregatedStatistic);

            // determine if delta percentage is greater than threshold
            delta = (currentAggregatedStatistic - previousAggregatedStatistic) / (100 * meanVolume);

            if (delta > thresholdPercentage) {
                decision = "sell";
            } else if (delta < -thresholdPercentage) {
                decision = "buy";
            } else decision = "hold";

            previousAggregatedStatistic = currentAggregatedStatistic;
            // print output to screen
            System.out.printf("%s,%s,%f,%f,%s\n", lastTimestamp, stockSymbol, lastClose, delta, decision);

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void configureConsumer(String brokerSocket, String groupId) {
        Properties props = new Properties();
        props.put("value.deserializer", "org.apache.kafka.connect.json.JsonDeserializer");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("bootstrap.servers", brokerSocket);
        props.put("group.id", groupId);
        props.put("auto.commit.enable", true);

        consumer = new KafkaConsumer<String, JsonNode>(props);
    }
}

