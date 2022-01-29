package com.poketcgdb

import com.fasterxml.jackson.databind.SerializationFeature
import com.poketcgdb.common.extensions.getDBPassword
import com.poketcgdb.common.extensions.getDBUrl
import com.poketcgdb.common.extensions.getDBUser
import com.poketcgdb.common.extensions.getRedisUrl
import com.poketcgdb.data.dao.UserDaoImpl
import com.poketcgdb.data.dao.interfaces.TokenDao
import com.poketcgdb.data.dao.TokenDaoImpl
import com.poketcgdb.data.dao.interfaces.UserDao
import com.poketcgdb.data.db.TokenDB
import com.poketcgdb.data.db.UserDB
import com.poketcgdb.data.repository.TokenRepositoryImpl
import com.poketcgdb.data.repository.UserRepositoryImpl
import com.poketcgdb.domain.repository.TokenRepository
import com.poketcgdb.domain.repository.UserRepository
import com.poketcgdb.resource.routes.login
import com.poketcgdb.resource.routes.refreshTokens
import com.poketcgdb.resource.routes.createUser
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {

    // Set up database
    UserDB.url = this.environment.getDBUrl()
    UserDB.userName = this.environment.getDBUser()
    UserDB.password = this.environment.getDBPassword()

    TokenDB.url = this.environment.getRedisUrl()

    // Connect to database
    UserDB.database

    // Create dependencies
    val userDao: UserDao = UserDaoImpl()
    val tokenDao: TokenDao = TokenDaoImpl(TokenDB)
    val tokenRepository: TokenRepository = TokenRepositoryImpl(tokenDao)
    val userRepository: UserRepository = UserRepositoryImpl(userDao)

    routing {
        route ("/auth") {
            refreshTokens(tokenRepository)
            login(userRepository, tokenRepository)
            route("/user") {
                createUser(userRepository)
            }
        }
    }

    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
}
