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
        // TODO::Initialise Kafka Producer with properties

    }

    public void send(final Transaction transaction) throws JsonProcessingException {
        // TODO::Serialise the transaction DTO to a JSON string using Jackson
        // TODO::See http://www.mkyong.com/java/jackson-2-convert-java-object-to-from-json/ for help


        // TODO::Create a producer record, the ProducerRecord(String topic, V value) constructor can be used
        // TODO::as no key is necessary


        // TODO::Use the Kafka producer to send the producer record

    }

    @Override
    public void close() throws Exception {
        // TODO::Close the Kafka producer

    }

}
