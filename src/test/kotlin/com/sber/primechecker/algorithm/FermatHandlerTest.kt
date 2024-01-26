package com.sber.primechecker.algorithm


import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class FermatHandlerTest {
    @Autowired
    private lateinit var fermatHandler: FermatHandler

    @Test
    fun isPrimeNumberReturnsFalseForComposite() {
        assertFalse { fermatHandler.isPrimeNumber(123, 100) }
    }

    @Test
    fun isPrimeNumberReturnsTrue() {
        assertTrue { fermatHandler.isPrimeNumber(131, 100) }
    }

    @Test
    fun getNameTest() {
        assertEquals("Fermat", fermatHandler.getName())
    }
}