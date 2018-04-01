package uk.co.bitcat.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import uk.co.bitcat.dto.Transaction;

import java.util.Properties;

public class TransactionProducer implements AutoCloseable {

    /** Kafka producer */
    protected Producer<String, String> producer;

    public TransactionProducer() {
        // Initialise Kafka Producer with properties
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(props);
    }

    public void send(final Transaction transaction) throws JsonProcessingException {
        // Serialise the transaction DTO to a JSON string using Jackson
        // See http://www.mkyong.com/java/jackson-2-convert-java-object-to-from-json/ for help
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(transaction);

        // Create a producer record, the ProducerRecord(String topic, V value) constructor can be used
        // as no key is necessary
        ProducerRecord<String, String> record = new ProducerRecord<>("test", json);

        // Use the Kafka producer to send the message
        producer.send(record);
        System.out.println("Sending payload: " + json);
    }

    @Override
    public void close() throws Exception {
        // Close the Kafka producer
        producer.close();
    }

}
