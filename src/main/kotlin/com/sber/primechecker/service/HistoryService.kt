package com.sber.primechecker.service

import com.sber.primechecker.entity.History
import com.sber.primechecker.entity.User
import com.sber.primechecker.repository.HistoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
open class HistoryService(
    @Autowired
    private val historyRepository: HistoryRepository
) {
    fun findByUser(user: User, pageable: Pageable): Page<History> = historyRepository.findByUser(user, pageable)

    fun findByNumberAndUser(user: User, number: Long) =
        historyRepository.findAllByUser(user).filter { history -> number == history.number }

    fun save(history: History) {
        historyRepository.save(history)
    }
}
