package com.sber.primechecker.algorithm

import org.springframework.stereotype.Component
import java.math.BigInteger
import java.util.*
import java.util.concurrent.*
import kotlin.concurrent.Volatile
import kotlin.math.abs
import kotlin.math.ceil
@Component
open class MillerRabinHandler(
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
            val millerRabinCallable = Callable {
                checkNumberIsPrime(number, ceil((iter / threadCount).toDouble()).toInt())
            }
            val future = executorService.submit(millerRabinCallable)
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
        return "Miller-Rabin"
    }

    private fun modPow(a: Long, b: Long, c: Long): Long {
        var res: Long = 1
        for (i in 0 until b) {
            res *= a
            res %= c
        }
        return res % c
    }

    private fun mulMod(a: Long, b: Long, mod: Long): Long {
        return BigInteger.valueOf(a).multiply(BigInteger.valueOf(b)).mod(BigInteger.valueOf(mod)).toLong()
    }

    private fun checkNumberIsPrime(numberToCheck: Long, iterations: Int): Boolean {
        if (!found) {
            var s = numberToCheck - 1
            while (s % 2 == 0L) s /= 2

            val rand = Random()
            for (i in 0 until iterations) {
                val r = abs(rand.nextLong().toDouble()).toLong()
                val a = r % (numberToCheck - 1) + 1
                var temp = s
                var mod = modPow(a, temp, numberToCheck)
                while (temp != numberToCheck - 1 && mod != 1L && mod != numberToCheck - 1) {
                    mod = mulMod(mod, mod, numberToCheck)
                    temp *= 2
                }
                if (mod != numberToCheck - 1 && temp % 2 == 0L) {
                    found = true
                    return false
                }
            }
        }
        return true
    }
}