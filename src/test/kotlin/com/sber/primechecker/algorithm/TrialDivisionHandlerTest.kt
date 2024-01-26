package com.sber.primechecker.algorithm


import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TrialDivisionHandlerTest {
    @Autowired
    private lateinit var trialDivisionHandler: TrialDivisionHandler

    @Test
    fun isPrimeNumberReturnsFalseForComposite() {
        assertFalse { trialDivisionHandler.isPrimeNumber(123, 100) }
    }

    @Test
    fun isPrimeNumberReturnsTrue() {
        assertTrue { trialDivisionHandler.isPrimeNumber(131, 100) }
    }

    @Test
    fun getNameTest() {
        assertEquals("Trial", trialDivisionHandler.getName())
    }
}