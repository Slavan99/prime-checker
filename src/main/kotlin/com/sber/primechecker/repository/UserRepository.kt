package com.sber.primechecker.repository

import com.sber.primechecker.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByName(name: String?): User?
    fun findByEmail(email: String?): User?

}