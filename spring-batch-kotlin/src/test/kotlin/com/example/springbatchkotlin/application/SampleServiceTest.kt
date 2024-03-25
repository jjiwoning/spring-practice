package com.example.springbatchkotlin.application

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SampleServiceTest{

    @Autowired
    private lateinit var sampleService: SampleService

    @Test
    fun test() {
        sampleService.hello()
        Thread.sleep(5000L)
    }
}
