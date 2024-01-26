package com.sber.primechecker.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class History(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Int? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    private val user: User? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    val algorithm: Algorithm? = null,

    val number: Long? = 0,

    val iterations: Int? = 0,

    val result: Boolean? = false,

    var checkDateTime: LocalDateTime
) {
    constructor() : this(1, null, null, null, null, null, LocalDateTime.now())
}