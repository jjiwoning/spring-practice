package com.example.springkotlinkafka.producer

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.assertj.core.api.Assertions.assertThat
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
@EmbeddedKafka(partitions = 3, brokerProperties = ["listeners=PLAINTEXT://localhost:9092"], ports = [9092])
class KafkaProducerTest(
    @Autowired private val embeddedKafkaBroker: EmbeddedKafkaBroker
) {
    val producer = KafkaProducer<String, String>(KafkaTestUtils.producerProps(embeddedKafkaBroker))

    @Test
    @DisplayName("Kafka Producer로 메시지를 보내고 blocking하여 동작 확인")
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

        assertThat(result.topic())
            .isEqualTo(topic)

        // producer는 Record 단위로 카프카 브로커에게 메시지 발행
        // produce의 결과를 비동기적으로 처리 가능
        // 만약 produce의 결과를 blocking하여 확인하지 않으면 메시지 누락 가능성 존재!
    }
}
