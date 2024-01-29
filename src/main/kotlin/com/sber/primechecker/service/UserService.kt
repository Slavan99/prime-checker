package com.sber.primechecker.service

import com.sber.primechecker.dto.UserDto
import com.sber.primechecker.entity.Role
import com.sber.primechecker.entity.User
import com.sber.primechecker.exception.IncorrectDataException
import com.sber.primechecker.exception.UserAlreadyExistAuthenticationException
import com.sber.primechecker.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

/**
 * Service for user operations
 */
@Service
class UserService(
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {


    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails = findByName(username)!!

    /**
     * Find user by name
     */
    fun findByName(username: String?): User? = userRepository.findByName(username)
    /**
     * Find user by email
     */
    fun findByEmail(email:String?):User? = userRepository.findByEmail(email)

    /**
     * Find all users
     */
    fun findAll(): List<User> = userRepository.findAll()

    /**
     * Enable/disable user, grant admin rights
     */
    fun handleUserActiveAndAdmin(userName: String?, active: Boolean, admin: Boolean) {
        val user: User = userRepository.findByName(userName)!!
        user.isActive = active
        if (admin) {
            user.addRole(Role.ADMIN)
        } else {
            user.removeRole(Role.ADMIN)
        }
        userRepository.save(user)
    }

    /**
     * Register user in the app
     */
    @Throws(Exception::class)
    fun registerUser(userDto: UserDto): User {
        if (userDto.name == "" || userDto.password == "") {
            throw IncorrectDataException("Incorrect name or password!")
        }
        val byName: User? = findByName(userDto.name)
        val byEmail: User? = findByEmail(userDto.email)
        if (byName != null) {
            throw UserAlreadyExistAuthenticationException("There is already an account registered with the same name!")
        }
        if (byEmail != null) {
            throw UserAlreadyExistAuthenticationException("There is already an account registered with the same email!")
        }
        val passwd = passwordEncoder.encode(userDto.password)
        val user = User(
            name = userDto.name!!,
            passwd = passwd,
            isActive = true,
            roles = mutableSetOf(Role.USER),
            email = userDto.email
        )
        return userRepository.save(user)
    }
}
