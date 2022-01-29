package com.poketcgdb.resource.payloads

import kotlinx.serialization.Serializable

@Serializable
data class IssuedTokensPayload(
    val accessToken: String,
    val refreshToken: String
)