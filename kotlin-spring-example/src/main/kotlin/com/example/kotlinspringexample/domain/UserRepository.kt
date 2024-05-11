package com.example.kotlinspringexample.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.history.RevisionRepository

interface UserRepository : JpaRepository<User, Long>, RevisionRepository<User, Long, Int> {

    fun findByLogin(login: String): User?
}
