package com.example.kotlinspringexample.domain

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class UserRepositoryTest @Autowired constructor(
    val entityManager: TestEntityManager,
    val userRepository: UserRepository
) {

    @Test
    @DisplayName("login을 바탕으로 User를 찾을 수 있다.")
    fun `When findByLogin then return User`() {
        // given
        val johnDoe = User(login = "johnDoe", firstname =  "John", lastname =  "Doe")
        entityManager.persist(johnDoe)
        entityManager.flush()

        // when
        val user = userRepository.findByLogin(johnDoe.login)

        // then
        Assertions.assertThat(user).isEqualTo(johnDoe)
    }
}
