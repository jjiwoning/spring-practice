package com.example.springbatchkotlin.domain

import jakarta.persistence.*

@Table(name = "members")
@Entity
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val name: String,
    val loginId: String
) {

    companion object {
        fun of(name: String, loginId: String): Member {
            return Member(id = null, name = name, loginId = loginId)
        }
    }
}
