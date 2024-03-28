package com.example.springbatchkotlin.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.history.RevisionRepository

interface MemberRepository: JpaRepository<Member, Long>, RevisionRepository<Member, Long, Int> {
}
