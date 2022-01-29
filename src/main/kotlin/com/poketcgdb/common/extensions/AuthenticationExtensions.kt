package com.poketcgdb.common.extensions

import io.ktor.auth.*

val Authentication.Feature.AUTH_JWT: String
    get() = "auth-jwt"

val Authentication.Feature.AUTH_BASIC: String
    get() = "auth-basic"

val Authentication.Feature.AUTH_FORM: String
    get() = "auth-form"