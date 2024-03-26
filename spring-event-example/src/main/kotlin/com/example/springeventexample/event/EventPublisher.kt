package com.example.springeventexample.event

import org.springframework.context.ApplicationEventPublisher

class EventPublisher(

) {

    companion object {
        private lateinit var applicationEventPublisher: ApplicationEventPublisher

        internal fun setPublisher(applicationEventPublisher: ApplicationEventPublisher) {
            this.applicationEventPublisher = applicationEventPublisher
        }

        fun publish(event: Any) {
            applicationEventPublisher.publishEvent(event)
        }
    }
}
