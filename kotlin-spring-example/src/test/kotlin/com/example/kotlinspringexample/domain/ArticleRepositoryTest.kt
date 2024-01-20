package com.example.kotlinspringexample.domain

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class ArticleRepositoryTest @Autowired constructor(
    val entityManager: TestEntityManager,
    val articleRepository: ArticleRepository
) {

    @Test
    @DisplayName("Article의 id로 Article을 조회할 수 있다.")
    fun `When findByIdOrNull then return Article`() {
        // given
        val johnDoe = User(login = "johnDoe", firstname =  "John", lastname =  "Doe")
        entityManager.persist(johnDoe)

        val article = Article(title = "Lorem", headline =  "Lorem", content =  "dolor sit amet", author = johnDoe)
        entityManager.persist(article)

        entityManager.flush()

        // when
        val found = articleRepository.findByIdOrNull(article.id!!)

        // then
        assertThat(found).isEqualTo(article)
    }

}
