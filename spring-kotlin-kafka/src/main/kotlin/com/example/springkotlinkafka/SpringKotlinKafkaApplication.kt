package com.example.springkotlinkafka

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringKotlinKafkaApplication

fun main(args: Array<String>) {
	runApplication<SpringKotlinKafkaApplication>(*args)
}
