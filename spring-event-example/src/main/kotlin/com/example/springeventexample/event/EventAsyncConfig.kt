package com.example.springeventexample.event

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@Configuration
@EnableAsync
class EventAsyncConfig {

    @Bean(name = ["AsyncEvent"])
    fun asyncPractice(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 2
        executor.maxPoolSize = 10
        executor.queueCapacity = 500
        executor.setThreadNamePrefix("AsyncEventThread-")
        executor.initialize()
        return executor
    }
}
