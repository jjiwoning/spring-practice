package com.example.springkotlinkafka.consumer

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.kafka.test.utils.KafkaTestUtils
import org.springframework.test.context.ContextConfiguration
import java.time.Duration
import java.util.stream.StreamSupport

@DisplayName("Kafka Consumer 학습 테스트")
@ContextConfiguration(classes = [SpringBootApplication::class])
@EmbeddedKafka(partitions = 3, brokerProperties = ["listeners=PLAINTEXT://localhost:9092"], ports = [9092])
class KafkaAutoCommitConsumerTest(
    @Autowired private val embeddedKafkaBroker: EmbeddedKafkaBroker
) {

    val producer = KafkaProducer<String, String>(KafkaTestUtils.producerProps(embeddedKafkaBroker))

    val consumer = KafkaConsumer<String, String>(buildKafkaConsumerProps())

    @Test
    @DisplayName("Consumer는 여러 개의 토픽을 consume할 수 있다.")
    fun test1() {
        // given
        val givenTopic = "test-topic"
        produce(givenTopic, "1")
        produce(givenTopic, "2")
        produce(givenTopic, "3")
        produce(givenTopic, "4")
        produce(givenTopic, "5")

        // when
        consumer.subscribe(listOf(givenTopic))
        val record1 = consumer.poll(Duration.ofSeconds(3L))
        val record2 = consumer.poll(Duration.ofSeconds(3L))

        // then
        val result1 = StreamSupport.stream(record1.spliterator(), false)
            .map { it.value() }
            .toList()
        val result2 = StreamSupport.stream(record2.spliterator(), false)
            .map { it.value() }
            .toList()

        Assertions.assertThat(result1)
            .isEqualTo(listOf("1", "2", "3"))

        Assertions.assertThat(result2)
            .isEqualTo(listOf("4", "5"))
    }

    private fun produce(givenTopic: String, value: String) {
        val producerRecord = ProducerRecord<String, String>(givenTopic, value)
        producer.send(producerRecord)
    }

    private fun buildKafkaConsumerProps(): Map<String, Any> {
        val consumerProps = KafkaTestUtils.consumerProps("test-consumer", "true", embeddedKafkaBroker)
        consumerProps["max.poll.records"] = "3"
        return consumerProps
    }
}
