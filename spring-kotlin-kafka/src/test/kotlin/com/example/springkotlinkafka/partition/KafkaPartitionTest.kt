package com.example.springkotlinkafka.partition

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

    @Test
    @DisplayName("토픽을 발행할 때 key 값을 맞추면 같은 파티션으로 토픽이 발행된다.")
    fun test2() {
        val topic = "test-topic"

        val map = mutableMapOf<Int, Int>()

        repeat(100) {
            val producerRecord1 = ProducerRecord(topic, getKey(it), "안녕하세요! ${it}")
            val partition = producer.send(producerRecord1).get().partition()
            map[partition] = map.getOrDefault(partition, 0) + 1
        }

        Assertions.assertThat(map).hasSize(2)
        map.forEach{
            Assertions.assertThat(it.value).isEqualTo(50)
        }
    }

    private fun getKafkaProduceProps(): MutableMap<String, Any>? {
        val props = KafkaTestUtils.producerProps(embeddedKafkaBroker)
        props["key.serializer"] = "org.apache.kafka.common.serialization.StringSerializer"
        return props
    }

    private fun getKey(number: Int): String {
        return if (number % 2 == 0) {
            "key-1111"
        } else {
            "key-2222"
        }
    }
}
