package com.example.springbatchkotlin.domain

import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.history.RevisionSort


@DisplayName("Spring Data Envers 학습 테스트")
@Transactional
@SpringBootTest
class MemberRepositoryTest {

    @Autowired lateinit var memberRepository: MemberRepository

    @Autowired lateinit var entityManager: EntityManager

    @Test
    @DisplayName("envers 적용 확인")
    fun test() {
        val member = Member(null, "hello", "hello")

        memberRepository.save(member)

        entityManager.flush()

        repeat(10) {
            val member2 = memberRepository.findById(member.id!!).get()
            val updateMember = Member(member2.id, "hello${it}", "hello${it}")
            memberRepository.save(updateMember)
            entityManager.flush()
        }

        println(member.id!!)

        val result = memberRepository.findRevisions(member.id!!, PageRequest.of(0, 10, RevisionSort.desc()))

        println(result)
        println(result.size)

        for (revision in result) {
            println(revision.entity.name)
        }
    }
}
