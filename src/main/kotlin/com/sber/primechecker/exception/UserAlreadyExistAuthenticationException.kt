package com.sber.primechecker.exception

import org.springframework.security.core.AuthenticationException

/**
 * Exception thrown when registering user with already existing credentials
 */
class UserAlreadyExistAuthenticationException(msg: String?) : AuthenticationException(msg)
