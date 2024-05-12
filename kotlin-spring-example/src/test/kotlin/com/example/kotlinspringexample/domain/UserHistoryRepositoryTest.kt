package com.example.kotlinspringexample.domain

import jakarta.persistence.EntityManager
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.DefaultTransactionDefinition

@SpringBootTest
class UserHistoryRepositoryTest @Autowired constructor(
    val userRepository: UserRepository,
    val userHistoryRepository: UserHistoryRepository,
    val entityManager: EntityManager,
    val transactionManager: PlatformTransactionManager
) {

    @Test
    @DisplayName("login을 바탕으로 User를 찾을 수 있다.")
    fun `When findByLogin then return User`() {
        // 첫 번째 트랜잭션 시작
        var def: TransactionDefinition = DefaultTransactionDefinition()
        var status: TransactionStatus = transactionManager.getTransaction(def)

        // given
        val johnDoe = User(login = "johnDoe", firstname = "John", lastname = "Doe")
        userRepository.save(johnDoe)

        entityManager.flush()
        transactionManager.commit(status) // 첫 번째 트랜잭션 커밋

        // 새로운 트랜잭션 시작
        status = transactionManager.getTransaction(def)

        // when
        val user = userRepository.findByLogin(johnDoe.login) ?: throw Exception("User not found")
        user.updateDescription("hello")

        entityManager.flush()
        transactionManager.commit(status) // 두 번째 트랜잭션 커밋

        // 또 다른 트랜잭션 시작하여 revision 결과 조회
        status = transactionManager.getTransaction(def)
        val latestModifiedUser = userHistoryRepository.findLatestModifiedUser(user.id!!)
            ?: throw Exception("Latest modified user not found")

        // then
        println(latestModifiedUser.description)
        transactionManager.commit(status) // 조회 트랜잭션 커밋
    }
}
