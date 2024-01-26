package com.sber.primechecker.repository

import com.sber.primechecker.entity.History
import com.sber.primechecker.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface HistoryRepository : JpaRepository<History, Int?> {
    fun findByUser(user: User, pageable: Pageable): Page<History>
    fun findAllByUser(user: User): List<History>
}