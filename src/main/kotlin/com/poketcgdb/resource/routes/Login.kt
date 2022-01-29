package com.poketcgdb.resource.routes

import com.poketcgdb.common.extensions.sendErrorResponse
import com.poketcgdb.common.extensions.sendSuccessResponse
import com.poketcgdb.common.utils.JWTUtil
import com.poketcgdb.common.utils.PasswordUtil
import com.poketcgdb.domain.repository.TokenRepository
import com.poketcgdb.domain.repository.UserRepository
import com.poketcgdb.resource.payloads.IssuedTokensPayload
import com.poketcgdb.resource.request.LoginRequestBody
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.routing.*
import java.util.*

fun Route.login(
    userRepository: UserRepository,
    tokenRepository: TokenRepository
) {
    post("/login") {
        val loginRequestBody = call.receive<LoginRequestBody>()
        val user = userRepository.getUserByEmail(loginRequestBody.email)

        if (user.email.isEmpty() || user.password.isEmpty()) {
            return@post call.sendErrorResponse(HttpStatusCode.Unauthorized, "Invalid credentials, please try again.")
        }

        if (loginRequestBody.email != user.email || !PasswordUtil.verifyPassword(loginRequestBody.password, user.password)) {
            return@post call.sendErrorResponse(HttpStatusCode.Unauthorized, "Invalid credentials, please try again.")
        }

        val accessUUID = UUID.randomUUID().toString()
        val refreshUUID = UUID.randomUUID().toString()
        val accessToken = JWTUtil.generateAccessToken(context.application.environment.config, user.email, accessUUID)
        val refreshToken = JWTUtil.generateRefreshToken(context.application.environment.config, user.email, refreshUUID)
        val accessTokenExpiryDate = JWTUtil.getTokenExpiry(accessToken)
        val refreshTokenExpiryDate = JWTUtil.getTokenExpiry(refreshToken)

        try {
            tokenRepository.storeAccessClaim(accessUUID, user.email, accessTokenExpiryDate)
            tokenRepository.storeRefreshClaim(refreshUUID, user.email, refreshTokenExpiryDate)
        } catch (e: Exception) {
            return@post call.sendErrorResponse(HttpStatusCode.ServiceUnavailable, "Something went wrong. ${e.localizedMessage}")
        }

        call.sendSuccessResponse(HttpStatusCode.OK, IssuedTokensPayload(accessToken, refreshToken))
    }
}