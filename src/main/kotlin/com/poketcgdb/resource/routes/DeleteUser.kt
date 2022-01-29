package com.poketcgdb.resource.routes

import com.poketcgdb.domain.repository.UserRepository
import io.ktor.routing.*

fun Route.deleteUser(userRepository: UserRepository) {
    post("/delete") {

    }
}