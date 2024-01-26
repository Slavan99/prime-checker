package com.sber.primechecker.service

import com.sber.com.sber.primechecker.dto.UserDto
import com.sber.primechecker.entity.Role
import com.sber.primechecker.entity.User
import com.sber.primechecker.repository.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootTest(classes = [UserService::class])
class UserServiceTest {

    @Autowired
    private lateinit var userService: UserService

    private val user1 = User(
        name = "123",
        passwd = "123",
        email = "123@mail.ru",
        isActive = true,
        roles = mutableSetOf(Role.USER)
    )

    private val user2 = User(
        name = "222",
        passwd = "123",
        email = "222@mail.ru",
        isActive = true,
        roles = mutableSetOf(Role.USER)
    )

    @MockBean
    private lateinit var userRepository: UserRepository

    @MockBean
    private lateinit var passwordEncoder: PasswordEncoder

    @BeforeEach
    fun setUp() {
        val str = "123"
        `when`(passwordEncoder.encode(str)).thenReturn(str)
        `when`(userRepository.findByName(str)).thenReturn(user1)
        `when`(userRepository.findAll()).thenReturn(listOf(user1))
        `when`(userRepository.save(user2)).thenReturn(user2)
    }

    @Test
    fun findByNameTest() {
        val actual = userService.findByName("123")
        assertEquals(user1, actual)
    }

    @Test
    fun findAllTest() {
        val actual = userService.findAll()
        assertEquals(listOf(user1), actual)
    }

    @Test
    fun registerUserTest() {
        val registeredUser = userService.registerUser(UserDto("222", "222@mail.ru", "123"))
        assertEquals(user2, registeredUser)
    }
}