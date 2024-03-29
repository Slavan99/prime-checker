package com.sber.primechecker.algorithm

import org.springframework.stereotype.Component
import java.util.concurrent.*
import kotlin.concurrent.Volatile
import kotlin.math.sqrt
@Component
open class TrialDivisionHandler(
    @Volatile
    private var found: Boolean = false,
    private val threadCount: Int = Runtime.getRuntime().availableProcessors()
) : IPrimeChecker {

    @Throws(ExecutionException::class, InterruptedException::class)
    override fun isPrimeNumber(number: Long, iter: Int): Boolean {
        found = false
        if (number % 2 == 0L) {
            return false
        }
        val executorService = Executors.newFixedThreadPool(threadCount)
        val futures: MutableList<Future<Boolean>> = ArrayList()

        for (i in 0 until threadCount) {
            val trialDivisionCallable = Callable { checkNumberIsPrime(number, (3 + 2 * i).toLong()) }
            val future = executorService.submit(trialDivisionCallable)
            futures.add(future)
        }
        var result = true
        for (future in futures) {
            result = result and future.get() as Boolean
        }
        executorService.shutdown()
        return result
    }


    override fun getName(): String {
        return "Trial"
    }

    private fun checkNumberIsPrime(numberToCheck: Long, startPos: Long): Boolean {
        val sqrt = sqrt(numberToCheck.toDouble())
        var i = startPos
        while (i <= sqrt) {
            if (!found) {
                if (numberToCheck % i == 0L) {
                    found = true
                    return false
                }
            }
            i += (2 * threadCount).toLong()
        }
        return true
    }
}