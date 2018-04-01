package uk.co.bitcat;

import uk.co.bitcat.dto.Transaction;
import uk.co.bitcat.generator.TransactionGenerator;
import uk.co.bitcat.kafka.TransactionProducer;

public class App {

    public static void main(String[] args) throws Exception {
        try (TransactionProducer txProducer = new TransactionProducer()) {

            TransactionGenerator txGenerator = new TransactionGenerator();

            while (true) {
                // Get next transaction
                Transaction txDto = txGenerator.generate();

                // Send to Kafka
                txProducer.send(txDto);

                // Wait for a while before we send a new transaction
                Thread.sleep(5000);
            }
        }
    }

}
