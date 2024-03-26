package com.example.springeventexample.event

import org.springframework.beans.factory.InitializingBean
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EventConfig(
    private val applicationContext: ApplicationContext
) {

    @Bean
    fun eventsInitializer(): InitializingBean {
        println("Init EventBean")
        return InitializingBean { EventPublisher.setPublisher(applicationContext) }
    }
}
