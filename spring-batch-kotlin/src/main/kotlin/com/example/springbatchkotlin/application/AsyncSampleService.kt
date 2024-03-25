package com.example.springbatchkotlin.application

import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class AsyncSampleService {

    @Async("AsyncPractice")
    fun hello() {
        repeat(100) {
            println("hello${it}")
        }
    }
}
