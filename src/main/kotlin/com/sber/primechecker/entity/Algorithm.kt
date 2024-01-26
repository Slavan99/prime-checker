package com.sber.primechecker.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Algorithm(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Int = 0,
    val name: String = ""
) {
    constructor() : this(0, "")

    override fun toString(): String = name

}