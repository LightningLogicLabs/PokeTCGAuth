package com.poketcgdb.resource.request

data class CreateAccountRequestBody(
    val email: String = "",
    val password: String = ""
)