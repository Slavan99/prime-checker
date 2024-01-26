package com.sber.primechecker.algorithm

import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.*
import kotlin.concurrent.Volatile
import kotlin.math.abs
import kotlin.math.ceil

@Component
open class SolovayStrassenHandler(
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
            val solovayStrassenCheckerThread =
                Callable { checkNumberIsPrime(number, ceil((iter / threadCount).toDouble()).toInt()) }
            val future = executorService.submit(solovayStrassenCheckerThread)
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
        return "Solovay-Strassen"
    }


    private fun jacobi(m: Long, n: Long): Long {
        var a = m
        var b = n
        if (b <= 0 || b % 2 == 0L) return 0
        var j = 1L
        if (a < 0) {
            a = -a
            if (b % 4 == 3L) j = -j
        }
        while (a != 0L) {
            while (a % 2 == 0L) {
                a /= 2
                if (b % 8 == 3L || b % 8 == 5L) j = -j
            }
            val temp = a
            a = b
            b = temp

            if (a % 4 == 3L && b % 4 == 3L) j = -j
            a %= b
        }
        if (b == 1L) return j
        return 0
    }

    private fun modPow(a: Long, b: Long, c: Long): Long {
        var res: Long = 1
        for (i in 0 until b) {
            res *= a
            res %= c
        }
        return res % c
    }

    private fun checkNumberIsPrime(numberToCheck: Long, iterations: Int): Boolean {
        if (!found) {
            val rand = Random()
            for (i in 0 until iterations) {
                val r = abs(rand.nextLong().toDouble()).toLong()
                val a = r % (numberToCheck - 1) + 1
                val jacobian = (numberToCheck + jacobi(a, numberToCheck)) % numberToCheck
                val mod = modPow(a, (numberToCheck - 1) / 2, numberToCheck)
                if (jacobian == 0L || mod != jacobian) {
                    found = true
                    return false
                }
            }
        }
        return true
    }
}