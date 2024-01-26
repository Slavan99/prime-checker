package com.sber.primechecker.service

import com.sber.primechecker.entity.Algorithm
import com.sber.primechecker.entity.History
import com.sber.primechecker.entity.Role
import com.sber.primechecker.entity.User
import com.sber.primechecker.repository.AlgorithmRepository
import com.sber.primechecker.repository.HistoryRepository
import com.sber.primechecker.repository.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import java.time.LocalDateTime

@SpringBootTest
@ComponentScan(
    "com.sber.primechecker.algorithm", "com.sber.primechecker.service"
)
@EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration::class])
class PrimeNumberServiceTest {
    private val algorithmName = "Trial"

    private val user = User(
        name = "123",
        passwd = "123",
        email = "123@mail.ru",
        isActive = true,
        roles = mutableSetOf(Role.USER)
    )
    private val algorithm = Algorithm(name = "Trial")
    private val history1 = History(
        algorithm = algorithm,
        user = user,
        number = 123,
        iterations = 100,
        result = false,
        checkDateTime = LocalDateTime.now()
    )
    private val history2 = History(
        algorithm = algorithm,
        user = user,
        number = 131,
        iterations = 50,
        result = true,
        checkDateTime = LocalDateTime.now()
    )
    private val historyList = listOf(history1, history2)

    @Autowired
    private lateinit var primeNumberService: PrimeNumberService

    @MockBean
    private lateinit var historyRepository: HistoryRepository

    @MockBean
    private lateinit var algorithmRepository: AlgorithmRepository

    @Autowired
    private lateinit var userService: UserService

    @MockBean
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun setUp() {
        `when`(historyRepository.findAllByUser(user)).thenReturn(historyList)
        `when`(algorithmRepository.findByName(algorithmName)).thenReturn(algorithm)
    }

    @Test
    fun checkNumberTestForPrimeNumberNotInHistory() {
        val actual = primeNumberService.checkNumber(user, algorithmName, 107, 100)
        Assertions.assertTrue(actual)
    }

    @Test
    fun checkNumberTestForPrimeNumberInHistory() {
        val actual = primeNumberService.checkNumber(user, algorithmName, 131, 100)
        Assertions.assertTrue(actual)
    }

    @Test
    fun checkNumberTestForCompositeNumberNotInHistory() {
        val actual = primeNumberService.checkNumber(user, algorithmName, 117, 100)
        Assertions.assertFalse(actual)
    }

    @Test
    fun checkNumberTestForCompositeNumberInHistory() {
        val actual = primeNumberService.checkNumber(user, algorithmName, 123, 100)
        Assertions.assertFalse(actual)
    }

}