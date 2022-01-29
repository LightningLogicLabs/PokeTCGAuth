package com.poketcgdb.resource.response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val status: Int,
    val errorMessage: String
)