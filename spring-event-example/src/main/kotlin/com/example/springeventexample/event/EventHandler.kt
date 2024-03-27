package com.example.springeventexample.event

import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class EventHandler {

    @Async("AsyncEvent")
    @EventListener
    fun sendHello(sampleEvent: SampleEvent) {
        println("${sampleEvent.hello} world!")
    }
}
