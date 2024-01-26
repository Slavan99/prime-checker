package com.sber.com.sber.primechecker.exception

import org.springframework.security.core.AuthenticationException

class UserAlreadyExistAuthenticationException(msg: String?) : AuthenticationException(msg)
