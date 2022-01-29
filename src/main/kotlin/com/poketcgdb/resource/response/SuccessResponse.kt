package com.poketcgdb.resource.response

import kotlinx.serialization.Serializable

@Serializable
data class SuccessResponse<T>(
    val status: Int,
    val payload: T
)