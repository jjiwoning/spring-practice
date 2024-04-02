package com.example.springkotlinkafka.producer

import org.apache.kafka.clients.producer.Callback
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
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
import java.lang.Exception

@DisplayName("Kafka Producer 학습 테스트")
@ContextConfiguration(classes = [SpringBootApplication::class])
@EmbeddedKafka(partitions = 3, brokerProperties = ["listeners=PLAINTEXT://localhost:9092"], ports = [9092])
class KafkaProduceCallbackTest (
    @Autowired private val embeddedKafkaBroker: EmbeddedKafkaBroker
) {

    val producer = KafkaProducer<String, String>(KafkaTestUtils.producerProps(embeddedKafkaBroker))

    @Test
    @DisplayName("콜백을 통해 produce 결과를 확인할 수 있다.")
    fun test1() {
        val topic = "test-topic"
        val producerRecord = ProducerRecord<String, String>(topic, "안녕하세요!")

        // Callback 을 등록하여 non-blocking 방식으로 확인할 수 있다
        producer.send(producerRecord, TestProduceCallback.getInstance())

        producer.close()
    }
}


class TestProduceCallback: Callback {

    companion object {
        fun getInstance(): TestProduceCallback {
            return TestProduceCallback();
        }
    }

    override fun onCompletion(p0: RecordMetadata?, p1: Exception?) {
        if (p0 != null) {
            println("""
                [메시지 발행 성공]: ${p0.topic()}, ${p0.partition()}, ${p0.offset()}, ${p0.timestamp()}
            """.trimIndent())
            return
        }
        println("[메시지 발행 실패!] ${p1}")
    }
}
