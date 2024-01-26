package com.sber.com.sber.primechecker.dto

data class NumberCheckDto(
    var inputNumber: String? = null,
    var inputAlgorithm: String? = "Trial",
    var inputIterations: String? = null,
)
