package com.poketcgdb.common.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.poketcgdb.common.utils.JWTUtil.JWTClaim.UserId
import com.poketcgdb.common.utils.JWTUtil.JWTClaim.RefreshUUID
import com.poketcgdb.common.utils.JWTUtil.JWTClaim.AccessUUID
import io.ktor.config.ApplicationConfig
import java.lang.System.currentTimeMillis
import java.util.Date
import java.util.concurrent.TimeUnit.DAYS
import java.util.concurrent.TimeUnit.MINUTES

object JWTUtil {

    enum class JWTClaim(val rawValue: String) {
        AccessUUID("access_uuid"),
        RefreshUUID("refresh_uuid"),
        UserId("user_id"),
    }

    fun generateAccessToken(config: ApplicationConfig, userId: String, accessUUID: String): String {
        val audience = config.property("ktor.jwt.audience").getString()
        val issuer = config.property("ktor.jwt.issuer").getString()
        val accessSecret = config.property("ktor.jwt.accessSecret").getString()
        val expiryDate = Date(currentTimeMillis() + MINUTES.toMillis(60))
        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim(AccessUUID.rawValue, accessUUID)
            .withClaim(UserId.rawValue, userId)
            .withExpiresAt(expiryDate)
            .sign(Algorithm.HMAC256(accessSecret))
    }

    fun generateRefreshToken(config: ApplicationConfig, userId: String, refreshUUID: String): String {
        val refreshSecret = config.property("ktor.jwt.refreshSecret").getString()
        val expiryDate = Date(currentTimeMillis() + DAYS.toMillis(7))
        return JWT.create()
            .withClaim(RefreshUUID.rawValue, refreshUUID)
            .withClaim(UserId.rawValue, userId)
            .withExpiresAt(expiryDate)
            .sign(Algorithm.HMAC256(refreshSecret))
    }

    fun getVerifier(config: ApplicationConfig): JWTVerifier {
        val accessSecret = config.property("ktor.jwt.accessSecret").getString()
        val audience = config.property("ktor.jwt.audience").getString()
        val issuer = config.property("ktor.jwt.issuer").getString()
        return JWT.require(Algorithm.HMAC256(accessSecret))
            .withAudience(audience)
            .withIssuer(issuer)
            .build()
    }

    fun getRefreshUUIDClaim(refreshToken: String): String =
        JWT.decode(refreshToken).claims[RefreshUUID.rawValue]?.asString() ?: ""

    fun getTokenExpiry(token: String): Date = JWT.decode(token).expiresAt
}