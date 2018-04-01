package uk.co.bitcat.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;
import uk.co.bitcat.dto.Transaction;
import uk.co.bitcat.dto.TransactionInput;
import uk.co.bitcat.dto.TransactionOutput;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import static org.easymock.EasyMock.expect;

public class TransactionProducerTest extends EasyMockSupport {

    @Rule
    public EasyMockRule rule = new EasyMockRule(this);

    @Mock
    private Producer<String, String> producer;

    @Mock
    private Future<RecordMetadata> recordMetadataFuture;

    @TestSubject
    private TransactionProducer txProducer = new TransactionProducer();

    @Test
    public void testSend() throws JsonProcessingException {
        // Ensure the mock producer is in use
        txProducer.producer = producer;

        TransactionInput txInput = new TransactionInput("tx1", 0);
        TransactionOutput txOutput = new TransactionOutput(100, "Account1");

        Map<Integer, TransactionInput> txInputs = new HashMap<>();
        txInputs.put(0, txInput);

        Map<Integer, TransactionOutput> txOutputs = new HashMap<>();
        txOutputs.put(0, txOutput);

        Transaction tx = new Transaction("tx0", txInputs, txOutputs);

        String json = "{\"id\":\"tx0\",\"inputs\":{\"0\":{\"txId\":\"tx1\",\"utxoIndex\":0}}," +
                "\"outputs\":{\"0\":{\"amount\":100,\"address\":\"Account1\"}}}";
        ProducerRecord<String, String> record = new ProducerRecord<>("test", json);

        expect(producer.send(record)).andReturn(recordMetadataFuture);

        replayAll();

        txProducer.send(tx);

        verifyAll();
    }

}
