package com.example.kotlinspringexample.domain

import com.example.kotlinspringexample.util.toSlug
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Article(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var title: String,
    var headline: String,
    var content: String,
    @ManyToOne(fetch = FetchType.LAZY)
    var author: User,
    var slug: String = title.toSlug(),
    var addedAt: LocalDateTime = LocalDateTime.now()
)
