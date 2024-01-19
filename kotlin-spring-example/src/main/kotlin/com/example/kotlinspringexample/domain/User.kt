package com.example.kotlinspringexample.domain

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    var login: String,
    var firstname: String,
    var lastname: String,
    var description: String? = null
)
