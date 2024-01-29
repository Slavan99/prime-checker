package com.sber.primechecker.service

import com.sber.primechecker.algorithm.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Component to get IPrimeChecker bean by algorithm name
 */
@Component
open class HandlerResolver(
    @Autowired private val trialDivisionHandler: TrialDivisionHandler,
    @Autowired private val fermatHandler: FermatHandler,
    @Autowired private val millerRabinHandler: MillerRabinHandler,
    @Autowired private val solovayStrassenHandler: SolovayStrassenHandler
) {

    private val handlersMap: Map<String, IPrimeChecker> = mapOf(
        trialDivisionHandler.getName() to trialDivisionHandler,
        fermatHandler.getName() to fermatHandler,
        millerRabinHandler.getName() to millerRabinHandler,
        solovayStrassenHandler.getName() to solovayStrassenHandler
    )

    fun getCheckerByName(name: String): IPrimeChecker {
        return handlersMap[name]!!
    }

}