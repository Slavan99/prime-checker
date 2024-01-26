package com.sber.primechecker.repository

import com.sber.primechecker.entity.Algorithm
import org.springframework.data.jpa.repository.JpaRepository

interface AlgorithmRepository : JpaRepository<Algorithm, Int?> {
    fun findByName(name: String?): Algorithm
}
