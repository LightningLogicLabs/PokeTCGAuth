package com.poketcgdb.resource.routes

import com.poketcgdb.domain.repository.TokenRepository
import io.ktor.routing.*

fun Route.refreshTokens(tokenRepository: TokenRepository) {
    post("/refreshTokens") {

    }
}