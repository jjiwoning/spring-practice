package com.example.springkotlinkafka.consumer

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.internals.Topic
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
class KafkaMultiTopicConsumerTest(
    @Autowired private val embeddedKafkaBroker: EmbeddedKafkaBroker
) {

    val producer = KafkaProducer<String, String>(KafkaTestUtils.producerProps(embeddedKafkaBroker))

    val consumer = KafkaConsumer<String, String>(KafkaTestUtils.consumerProps(
        "test-consumer", "true", embeddedKafkaBroker)
    )

    @Test
    @DisplayName("Consumer는 여러 개의 토픽을 consume할 수 있다.")
    fun test1() {
        // given
        val givenTopic = "test-topic"
        val producerRecord = ProducerRecord<String, String>(givenTopic, "안녕하세요!")
        producer.send(producerRecord)

        val givenTopic2 = "test-topic2"
        val producerRecord2 = ProducerRecord<String, String>(givenTopic2, "안녕하세요123")
        producer.send(producerRecord2)

        // when
        consumer.subscribe(listOf(givenTopic, givenTopic2))

        // then
        val record = consumer.poll(Duration.ofSeconds(3L))

        val value: String = StreamSupport.stream(record.spliterator(), false)
            .filter{ i -> i.topic() == givenTopic }
            .findFirst()
            .orElseGet(null)
            .value()
        val value2: String = StreamSupport.stream(record.spliterator(), false)
            .filter{ i -> i.topic() == givenTopic2 }
            .findFirst()
            .orElseGet(null)
            .value()

        println(value)
        println(value2)

        Assertions.assertThat(value).isEqualTo("안녕하세요!")
        Assertions.assertThat(value2).isEqualTo("안녕하세요123")
    }
}
