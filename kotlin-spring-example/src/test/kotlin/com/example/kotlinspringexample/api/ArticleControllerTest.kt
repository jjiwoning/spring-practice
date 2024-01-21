package com.example.kotlinspringexample.api

import com.example.kotlinspringexample.domain.Article
import com.example.kotlinspringexample.domain.ArticleRepository
import com.example.kotlinspringexample.domain.User
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest
class ArticleControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var articleRepository: ArticleRepository

    @Test
    fun `List articles`() {
        val johnDoe = User(login = "johnDoe", firstname =  "John", lastname =  "Doe")

        val lorem5Article = Article(title = "Lorem", headline =  "Lorem", content =  "dolor sit amet", author = johnDoe)
        val ipsumArticle = Article(title = "Ipsum", headline =  "Ipsum", content =  "dolor sit amet", author = johnDoe)

        every { articleRepository.findAllByOrderByAddedAtDesc() } returns listOf(lorem5Article, ipsumArticle)
        mockMvc.perform(get("/api/article/").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].author.login").value(johnDoe.login))
            .andExpect(jsonPath("\$.[0].slug").value(lorem5Article.slug))
            .andExpect(jsonPath("\$.[1].author.login").value(johnDoe.login))
            .andExpect(jsonPath("\$.[1].slug").value(ipsumArticle.slug))
    }
}
