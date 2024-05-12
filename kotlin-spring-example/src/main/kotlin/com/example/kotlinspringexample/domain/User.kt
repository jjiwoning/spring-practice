package com.example.kotlinspringexample.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.envers.Audited

@Audited
@Entity
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    var login: String,
    var firstname: String,
    var lastname: String,
    var description: String? = null
) {

    fun updateDescription(description: String) {
        this.description = description
    }
}
