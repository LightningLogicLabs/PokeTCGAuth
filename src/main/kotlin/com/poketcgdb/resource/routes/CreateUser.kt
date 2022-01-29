package com.poketcgdb.resource.routes

import com.poketcgdb.common.extensions.isValidEmail
import com.poketcgdb.common.extensions.sendErrorResponse
import com.poketcgdb.common.extensions.sendSuccessResponse
import com.poketcgdb.common.utils.PasswordUtil
import com.poketcgdb.domain.model.User
import com.poketcgdb.domain.repository.UserRepository
import com.poketcgdb.resource.request.CreateAccountRequestBody
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.routing.*

fun Route.createUser(
    userRepository: UserRepository
) {
    post("/create") {
        val createAccountRequestBody = call.receive<CreateAccountRequestBody>()
        val email = createAccountRequestBody.email
        val password = createAccountRequestBody.password
        val requestErrors = mutableListOf<String>()

        if (email.isEmpty() || !email.isValidEmail()) {
            requestErrors.add("Email is missing or has an incorrect format")
        }

        if (password.isEmpty()) {
            requestErrors.add("Password is missing or does not meet requirements")
        }

        if (requestErrors.isNotEmpty()) {
            return@post call.sendErrorResponse(HttpStatusCode.BadRequest, requestErrors.toString())
        }

        val hashedPassword = PasswordUtil.hashPassword(password)

        if (hashedPassword.isNullOrEmpty()) {
            return@post call.sendErrorResponse(HttpStatusCode.InternalServerError, "Something went wrong")
        }

        val status = userRepository.createUser(User(email = email, password = hashedPassword))

        when (!status.first) {
            true -> call.sendErrorResponse(HttpStatusCode.InternalServerError, status.second)
            false -> call.sendSuccessResponse(HttpStatusCode.OK, "User created")
        }
    }
}
