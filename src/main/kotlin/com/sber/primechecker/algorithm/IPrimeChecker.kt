package com.sber.primechecker.algorithm

import java.util.concurrent.ExecutionException

interface IPrimeChecker {
    @Throws(ExecutionException::class, InterruptedException::class)
    fun isPrimeNumber(number: Long, iter: Int): Boolean


    fun getName(): String {
        return "IPrimeChecker"
    }

    fun phi(n: Long): Long {
        var num = n
        var result = num
        var i: Long = 2
        while (i * i <= num) {
            if (num % i == 0L) {
                while (num % i == 0L) num /= i
                result -= result / i
            }
            ++i
        }
        if (num > 1) result -= result / num
        return result
    }
}