package com.sber.primechecker.service

import com.sber.primechecker.entity.Algorithm
import com.sber.primechecker.repository.AlgorithmRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class AlgorithmService(
    @Autowired
    private val algorithmRepository: AlgorithmRepository
) {

    fun findByName(name: String?): Algorithm = algorithmRepository.findByName(name)


    fun findAll(): MutableList<Algorithm> = algorithmRepository.findAll()

}