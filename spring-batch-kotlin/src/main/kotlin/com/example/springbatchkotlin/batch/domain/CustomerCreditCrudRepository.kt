package com.example.springbatchkotlin.batch.domain

import org.springframework.data.repository.CrudRepository

interface CustomerCreditCrudRepository: CrudRepository<CustomerCredit, Long> {
}
