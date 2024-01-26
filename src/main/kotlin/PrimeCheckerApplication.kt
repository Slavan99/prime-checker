package com.sber

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
open class PrimeCheckerApplication

fun main(args: Array<String>) {
    runApplication<PrimeCheckerApplication>(*args)
}