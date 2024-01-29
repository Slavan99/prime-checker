package com.sber.primechecker.service

import com.sber.primechecker.algorithm.IPrimeChecker
import com.sber.primechecker.entity.History
import com.sber.primechecker.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.concurrent.ExecutionException

@Component
class PrimeNumberService(
    @Autowired private val handlerResolver: HandlerResolver,
    @Autowired private val algorithmService: AlgorithmService,
    @Autowired private val historyService: HistoryService
) {
    /**
     * Check if a number is prime
     */
    @Throws(ExecutionException::class, InterruptedException::class)
    fun checkNumber(currentUser: User, algorithmName: String, number: Long, iterations: Int): Boolean {
        val primeChecker: IPrimeChecker = handlerResolver.getCheckerByName(algorithmName)
        val historiesByNumberAndUser = historyService.findByNumberAndUser(currentUser, number)
        val numberCheckResult: Boolean = if (historiesByNumberAndUser.isNotEmpty()) {
            historiesByNumberAndUser[0].result!!
        } else {
            primeChecker.isPrimeNumber(number, iterations)
        }
        val historyAdd = History(
            algorithm = algorithmService.findByName(algorithmName),
            user = currentUser,
            result = numberCheckResult,
            number = number,
            iterations = iterations,
            checkDateTime = LocalDateTime.now()
        )
        historyService.save(historyAdd)
        return numberCheckResult
    }
}