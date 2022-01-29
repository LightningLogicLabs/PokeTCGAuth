package com.poketcgdb.common.extensions

import com.poketcgdb.resource.response.ErrorResponse
import com.poketcgdb.resource.response.SuccessResponse
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*

// region Application Call Extensions
suspend inline fun <reified T> ApplicationCall.sendSuccessResponse(
    httpStatusCode: HttpStatusCode,
    payload: T
) {
    respond(httpStatusCode, SuccessResponse(httpStatusCode.value, payload))
}

suspend fun ApplicationCall.sendErrorResponse(
    httpStatusCode: HttpStatusCode,
    errorMessage: String = ""
) {
    respond(httpStatusCode, ErrorResponse(httpStatusCode.value, errorMessage))
}
// endregion Application Call Extensions

// region Application Config Extensions
fun ApplicationEnvironment.isInDevelopmentMode() = this.config.property("ktor.development").getString().toBoolean()

fun ApplicationEnvironment.getDBUrl() = when (isInDevelopmentMode()) {
    true -> this.config.property("ktor.database.devUrl").getString()
    false -> this.config.property("ktor.database.prodUrl").getString()
}

fun ApplicationEnvironment.getDBUser() = when (isInDevelopmentMode()) {
    true -> this.config.property("ktor.database.devUser").getString()
    false -> this.config.property("ktor.database.prodUser").getString()
}

fun ApplicationEnvironment.getDBPassword() = when (isInDevelopmentMode()) {
    true -> this.config.property("ktor.database.devPassword").getString()
    false -> this.config.property("ktor.database.prodPassword").getString()
}

fun ApplicationEnvironment.getRedisUrl() = when (isInDevelopmentMode()) {
    true -> this.config.property("ktor.redis.devUrl").getString()
    false -> this.config.property("ktor.redis.prodUrl").getString()
}
// endregion Application Config Extensions