package com.example.springkotlinkafka.producer

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Timeout
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.kafka.test.utils.KafkaTestUtils
import org.springframework.test.context.ContextConfiguration

@DisplayName("Kafka Producer 학습 테스트")
@ContextConfiguration(classes = [SpringBootApplication::class])
@EmbeddedKafka(
    partitions = 3,
    brokerProperties = ["listeners=PLAINTEXT://localhost:9092", "auto.create.topics.enable=false"],
    ports = [9092]
)
class KafkaProducerNotExistTopicTest(
    @Autowired private val embeddedKafkaBroker: EmbeddedKafkaBroker
) {
    val producer = KafkaProducer<String, String>(KafkaTestUtils.producerProps(embeddedKafkaBroker))

    @Test
    @Timeout(1)
    @DisplayName("만약 존재하지 않는 토픽을 producing하면 정상 실행되지 않는다.")
    fun test1() {
        val topic = "test-topic"

        // 발행하고자 하는 메시지 정의하기
        // 카프카는 Record 사용
        // 첫 인자: topic, 두 번째 인자: message payload
        val producerRecord = ProducerRecord<String, String>(topic, "안녕하세요!")

        // producer를 통해 record를 Kafka 브로커에게 전송
        val resultFuture = producer.send(producerRecord)

        // Future.get() 메서드를 통해 블로킹
        val result = resultFuture.get()

        Assertions.assertThat(result.topic())
            .isEqualTo(topic)
    }
}
