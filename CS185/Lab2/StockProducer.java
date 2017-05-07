package Lab2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class StockProducer {
    // Set the stream and topic to publish to.
    public static String topic;
    private static boolean verbose = true;

    // Declare a new producer
    public static KafkaProducer<String, JsonNode> producer;

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException, TimeoutException {
        // check command-line args
        if (args.length != 5) {
            System.err.println("usage: StockProducer <broker-socket> <input-file> <stock-symbol> <output-topic> <sleep-time>");
            System.err.println("eg: StockProducer localhost:9092 /user/user01/LAB2/orcl.csv orcl prices 1000");
            System.exit(1);
        }

        // initialize variables
        String brokerSocket = args[0];
        String inputFile = args[1];
        String stockSymbol = args[2];
        String outputTopic = args[3];
        topic = outputTopic;
        long sleepTime = Long.parseLong(args[4]);


        // configure the producer
        configureProducer(brokerSocket);

        // create a buffered file reader for the input file
        BufferedReader in = new BufferedReader(new FileReader(inputFile));
        String input;

        // loop through all lines in input file
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Scanner tokenizer;

        if (verbose) System.out.println("done overhead");
        while ((input = in.readLine()) != null) {
            try {
                // filter out "bad" records
                tokenizer = new Scanner(input).useDelimiter("\\s*,\\s*");
                tokenizer.next(); //skip index
                String date = tokenizer.next();
                df.parse(date);
                // create an ObjectNode to store data in
                // parse out the fields from the line and create key-value pairs in ObjectNode
                ObjectNode value = JsonNodeFactory.instance.objectNode();
                value.put("timestamp", date);
                value.put("open", tokenizer.nextDouble());
                value.put("high", tokenizer.nextDouble());
                value.put("low", tokenizer.nextDouble());
                value.put("close", tokenizer.nextDouble());
                value.put("volume", tokenizer.nextLong());

                if (verbose) System.out.println(value.toString());
                producer.send(new ProducerRecord<String, JsonNode>(topic, stockSymbol, value));
                // sleep the thread
                Thread.sleep(sleepTime);

            } catch (Exception e) {
                continue;
            }
        }

        System.out.println("finished!");

        // close buffered reader
        in.close();

        // close producer
        producer.close();
    }

    public static void configureProducer(String brokerSocket) {
        Properties props = new Properties();
        props.put("value.serializer", "org.apache.kafka.connect.json.JsonSerializer");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("bootstrap.servers", brokerSocket);
        producer = new KafkaProducer<String, JsonNode>(props);
    }
}
