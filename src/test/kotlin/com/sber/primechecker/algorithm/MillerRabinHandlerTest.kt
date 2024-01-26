package com.sber.primechecker.algorithm


import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MillerRabinHandlerTest {
    @Autowired
    private lateinit var millerRabinHandler: MillerRabinHandler

    @Test
    fun isPrimeNumberReturnsFalseForComposite() {
        assertFalse { millerRabinHandler.isPrimeNumber(123, 100) }
    }

    @Test
    fun isPrimeNumberReturnsTrue() {
        assertTrue { millerRabinHandler.isPrimeNumber(131, 100) }
    }

    @Test
    fun getNameTest() {
        assertEquals("Miller-Rabin", millerRabinHandler.getName())
    }
}