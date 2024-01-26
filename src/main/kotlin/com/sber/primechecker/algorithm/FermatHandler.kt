package com.sber.primechecker.algorithm

import org.springframework.stereotype.Component
import java.util.concurrent.*
import kotlin.concurrent.Volatile
import kotlin.math.ceil

@Component
open class FermatHandler(
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
            val fermatCallable =
                Callable { checkNumberIsPrime(number, ceil((iter / threadCount).toDouble()).toInt()) }
            val future = executorService.submit(fermatCallable)
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
        return "Fermat"
    }


    private fun binaryPower(a: Long, n: Long): Long {
        var aModN = a % n
        var res: Long = 1
        var nMinusOne = n - 1
        while (nMinusOne > 0) {
            if (nMinusOne % 2 == 1L) res = res * aModN % n
            aModN = aModN * aModN % n
            nMinusOne = nMinusOne shr 1
        }
        return res
    }

    private fun checkNumberIsPrime(numberToCheck: Long, iterations: Int): Boolean {
        if (!found) {
            for (i in 0 until iterations) {
                val a = (Math.random() * (numberToCheck - 3)) + 2
                if (binaryPower(a.toLong(), numberToCheck) != 1L) {
                    found = true
                    return false
                }
            }
        }
        return true
    }

}