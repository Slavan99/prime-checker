package com.sber.com.sber.primechecker.algorithm

import java.util.concurrent.*
import kotlin.concurrent.Volatile
import kotlin.math.sqrt

class TrialDivisionHandler : IPrimeChecker {

    @Volatile
    private var found = false
    private val threadCount = Runtime.getRuntime().availableProcessors()


    @Throws(ExecutionException::class, InterruptedException::class)
    override fun isPrimeNumber(number: Long, iter: Int): Boolean {
        found = false
        if (number % 2 == 0L) {
            return false
        }
        val executorService = Executors.newFixedThreadPool(threadCount)
        val futures: MutableList<Future<Boolean>> = ArrayList()

        class TrialDivisionThreadCallable(var numberToCheck: Long, var startPos: Long) : Callable<Boolean> {
            override fun call(): Boolean {
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

        fun Callable<Boolean>.call(numberToCheck: Long, startPos: Long): Boolean {
            val sqrt = sqrt(numberToCheck.toDouble())
            var i = startPos
            println("Поиск с позиции %d".format(i))
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
        for (i in 0 until threadCount) {
            val threadCallable = Callable { false }
            threadCallable.call(number, (3 + 2 * i).toLong())
            val future = executorService.submit(threadCallable)
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


}