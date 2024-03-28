package com.example.springbatchkotlin.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(basePackages = ["com.example.springbatchkotlin"], repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean::class)
class JpaConfig {
}