package com.sber.primechecker.service

import com.sber.primechecker.entity.Algorithm
import com.sber.primechecker.repository.AlgorithmRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest(classes = [AlgorithmService::class])
class AlgorithmServiceTest {

    private val trialDivisionAlgorithm = Algorithm(name = "Trial")
    private val fermatAlgorithm = Algorithm(name = "Fermat")
    private val algorithmList = listOf(trialDivisionAlgorithm, fermatAlgorithm)

    @Autowired
    private lateinit var algorithmService: AlgorithmService

    @MockBean
    private lateinit var algorithmRepository: AlgorithmRepository

    @BeforeEach
    fun setUp() {
        Mockito.`when`(algorithmRepository.findByName("Trial")).thenReturn(trialDivisionAlgorithm)
        Mockito.`when`(algorithmRepository.findAll()).thenReturn(algorithmList)
    }

    @Test
    fun findByNameTest() {
        val actual = algorithmService.findByName("Trial")
        Assertions.assertEquals(trialDivisionAlgorithm, actual)
    }

    @Test
    fun findAllTest() {
        val actual = algorithmService.findAll()
        Assertions.assertEquals(algorithmList, actual)
    }
}