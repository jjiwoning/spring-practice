package com.example.springbatchkotlin.batch.configuration

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager


@Configuration
class SimpleJobConfig {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Bean(name = ["simpleJob1"])
    fun simpleJob1(jobRepository: JobRepository, simpleStep1: Step): Job {
        log.info(">>> simpleJob1")
        return JobBuilder("simpleJob1", jobRepository)
            .start(simpleStep1)
            .build()
    }

    @Bean("simpleStep1")
    fun simpleStep1(
        jobRepository: JobRepository,
        testTasklet: Tasklet,
        platformTransactionManager: PlatformTransactionManager
    ): Step {
        log.info(">>> simpleStep1")
        return StepBuilder("simpleStep1", jobRepository)
            .tasklet(testTasklet, platformTransactionManager).build()
    }

    @Bean
    fun testTasklet(): Tasklet {
        return Tasklet { contribution: StepContribution, chunkContext: ChunkContext ->
            log.info(">>>>> Tasklet")
            RepeatStatus.FINISHED
        }
    }
}
