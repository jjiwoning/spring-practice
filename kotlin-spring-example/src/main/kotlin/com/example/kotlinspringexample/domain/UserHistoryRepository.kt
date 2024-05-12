package com.example.kotlinspringexample.domain

import org.hibernate.envers.AuditReader
import org.hibernate.envers.query.AuditEntity
import org.springframework.stereotype.Repository

@Repository
class UserHistoryRepository(
    private val auditReader: AuditReader
) {

    fun findLatestModifiedUser(id: Long): User? {
        val results = auditReader.createQuery()
            .forRevisionsOfEntity(User::class.java, true, false)
            .add(AuditEntity.id().eq(id))
            .addOrder(AuditEntity.revisionNumber().desc())
            .setMaxResults(1)
            .resultList

        if (results.isEmpty()) {
            return null
        }
        return results[0] as User
    }
}
