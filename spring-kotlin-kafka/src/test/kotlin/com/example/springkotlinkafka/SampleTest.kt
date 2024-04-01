package com.example.springkotlinkafka

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.kafka.test.utils.KafkaTestUtils
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration(classes = [SpringBootApplication::class])
@EmbeddedKafka(partitions = 3, brokerProperties = ["listeners=PLAINTEXT://localhost:9092"], ports = [9092])
class SampleTest(
    @Autowired private val embeddedKafkaBroker: EmbeddedKafkaBroker
) {

    val producer = KafkaProducer<String, String>(KafkaTestUtils.producerProps(embeddedKafkaBroker))

    @Test
    fun sample() {
        val record = ProducerRecord<String, String>("a-topic", "hi~")

        producer.send(record)
    }

    @AfterEach
    fun tearDown() {
        producer.close()
    }
}
