package com.sber

import com.sber.com.sber.primechecker.algorithm.TrialDivisionHandler

fun main() {
    val primeNumber = TrialDivisionHandler().isPrimeNumber(9223372036854775807, 0)
    println(primeNumber)
}