# Bank to the Future - Kafka

## Steps

1. Complete steps 1 to 5 of https://kafka.apache.org/quickstart then stop the kafka-console-producer.
1. Modify the first JavaDoc example at https://kafka.apache.org/11/javadoc/index.html?org/apache/kafka/clients/producer/KafkaProducer.html to complete the uk.co.bitcat.kafka.TransactionProducer class using the comments in the class for guidance.
1. To unit test run the uk.co.bitcat.kafka.TransactionProducerTest.
1. To system test, run your code as indicated in the 'To Run' section below.
1. You should see messages being received in the kafka-console-consumer started in step 1.

## To Run

* Right click the App class in an IDE such as IntelliJ or Eclipse and click run.
* Alternatively run `mvn exec:java` in a terminal in the same directory as this README after a `mvn clean install`.