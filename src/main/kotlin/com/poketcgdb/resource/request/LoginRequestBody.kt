package com.poketcgdb.resource.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestBody(
    val email: String = "",
    val password: String = ""
)