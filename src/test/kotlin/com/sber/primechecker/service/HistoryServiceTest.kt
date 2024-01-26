package com.sber.primechecker.service

import com.sber.primechecker.entity.Algorithm
import com.sber.primechecker.entity.History
import com.sber.primechecker.entity.Role
import com.sber.primechecker.entity.User
import com.sber.primechecker.repository.HistoryRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import java.time.LocalDateTime

@SpringBootTest(classes = [HistoryService::class])
class HistoryServiceTest {
    private val user = User(
        name = "123",
        passwd = "123",
        email = "123@mail.ru",
        isActive = true,
        roles = mutableSetOf(Role.USER)
    )
    private val algorithm = Algorithm(name = "Fermat")
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
    private val pageable: Pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "checkDateTime")
    private val historyList = listOf(history1, history2)


    @Autowired
    private lateinit var historyService: HistoryService

    @MockBean
    private lateinit var historyRepository: HistoryRepository

    @BeforeEach
    fun setUp() {
        `when`(historyRepository.findByUser(user, pageable)).thenReturn(PageImpl(historyList))
        `when`(historyRepository.findAllByUser(user)).thenReturn(historyList)
    }

    @Test
    fun findByUserTest() {
        val actual = historyService.findByUser(user, pageable)
        Assertions.assertEquals(PageImpl(historyList), actual)
    }

    @Test
    fun findByNumberAndUserTest() {
        val actual = historyService.findByNumberAndUser(user, 123)
        Assertions.assertEquals(historyList.filter { history -> history.number == 123L }, actual)
    }

}