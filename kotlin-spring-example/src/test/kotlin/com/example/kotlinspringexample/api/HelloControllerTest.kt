package com.example.kotlinspringexample.api

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HelloControllerTest(
        @Autowired val restTemplate: TestRestTemplate
) {

    @BeforeAll
    fun setup() {
        println(">> Setup")
    }

    @Test
    @DisplayName("HelloController 테스트")
    fun test() {
        val result = restTemplate.getForEntity<String>("/")

        Assertions.assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        Assertions.assertThat(result.body).isEqualTo("hello!")
    }

    @AfterAll
    fun teardown() {
        println(">> Tear down")
    }

}
