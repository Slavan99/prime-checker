package com.sber.primechecker.service

import com.sber.primechecker.entity.History
import com.sber.primechecker.entity.User
import com.sber.primechecker.repository.HistoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

/**
 * Service for operations with check history
 */
@Service
open class HistoryService(
    @Autowired
    private val historyRepository: HistoryRepository
) {
    /**
     * Get all check history for user with page separation
     */
    fun findByUser(user: User, pageable: Pageable): Page<History> = historyRepository.findByUser(user, pageable)

    /**
     * Get all check history for specific number and user
     */
    fun findByNumberAndUser(user: User, number: Long) =
        historyRepository.findAllByUser(user).filter { history -> number == history.number }

    /**
     * Save number check history to the DB
     */
    fun save(history: History) {
        historyRepository.save(history)
    }
}
