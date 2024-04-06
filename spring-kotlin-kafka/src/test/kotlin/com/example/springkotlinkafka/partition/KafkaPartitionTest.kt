package com.example.springkotlinkafka.partition

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.kafka.test.utils.KafkaTestUtils
import org.springframework.test.context.ContextConfiguration

@DisplayName("Kafka Producer 학습 테스트")
@ContextConfiguration(classes = [SpringBootApplication::class])
@EmbeddedKafka(partitions = 2, brokerProperties = ["listeners=PLAINTEXT://localhost:9092"], ports = [9092])
class KafkaPartitionTest(
    @Autowired private val embeddedKafkaBroker: EmbeddedKafkaBroker
) {
    val producer = KafkaProducer<String, String>(getKafkaProduceProps())

    @Test
    @DisplayName("Key based Partition 학습 테스트")
    fun test1() {
        val topic = "test-topic"

        repeat(6) {
            val producerRecord = ProducerRecord(topic, getKey(it), "안녕하세요! ${it}")
            producer.send(producerRecord)
        }
    }

    private fun getKafkaProduceProps(): MutableMap<String, Any>? {
        val props = KafkaTestUtils.producerProps(embeddedKafkaBroker)
        props["key.serializer"] = "org.apache.kafka.common.serialization.StringSerializer"
        return props
    }

    private fun getKey(number: Int): String {
        return if (number % 2 == 0) {
            "key-1"
        } else {
            "key-2"
        }
    }
}
