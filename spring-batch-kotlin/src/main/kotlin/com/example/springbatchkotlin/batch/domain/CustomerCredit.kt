package com.example.springbatchkotlin.batch.domain

import jakarta.persistence.*
import java.math.BigDecimal

@Table(name = "customer_credits")
@Entity
class CustomerCredit(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    name: String,
    credit: BigDecimal
) {

    var name: String = name
        protected set

    var credit: BigDecimal = credit
        protected set
}