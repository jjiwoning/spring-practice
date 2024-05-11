package com.example.kotlinspringexample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean::class)
@SpringBootApplication
class KotlinSpringExampleApplication

fun main(args: Array<String>) {
    runApplication<KotlinSpringExampleApplication>(*args)
}
