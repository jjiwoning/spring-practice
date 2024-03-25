package com.example.springbatchkotlin.application

import org.springframework.stereotype.Service

@Service
class SampleService(
    private val asyncSampleService: AsyncSampleService
) {

    fun hello() {
        println("SampleService Run")
        asyncSampleService.hello()
        println("SampleService End")
    }
}
