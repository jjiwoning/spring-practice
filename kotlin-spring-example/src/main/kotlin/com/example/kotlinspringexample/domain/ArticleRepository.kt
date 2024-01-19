package com.example.kotlinspringexample.domain

import org.springframework.data.jpa.repository.JpaRepository

interface ArticleRepository : JpaRepository<Article, Long> {
    fun findAllByOrderByAddedAtDesc(): Iterable<Article>
    fun findBySlug(slug: String): Article?
}
