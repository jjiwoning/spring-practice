package com.example.springbatchkotlin.batch.domain

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.repository.PagingAndSortingRepository
import java.math.BigDecimal


interface CustomerCreditPagingAndSortingRepository: PagingAndSortingRepository<CustomerCredit, Long> {

    fun findByCreditGreaterThan(credit: BigDecimal, request: Pageable): Slice<CustomerCredit>
}
