package com.example.kotlinspringexample.api

import com.example.kotlinspringexample.domain.User
import com.example.kotlinspringexample.domain.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/user")
class UserController(private val userRepository: UserRepository) {

    @GetMapping("/")
    fun findAll(): List<User> = userRepository.findAll()

    @GetMapping("/{login}")
    fun findOne(@PathVariable login: String) =
        userRepository.findByLogin(login) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "This user does not exist"
        )
}
