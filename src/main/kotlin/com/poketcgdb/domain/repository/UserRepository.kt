package com.poketcgdb.domain.repository

import com.poketcgdb.domain.model.User

interface UserRepository {

    suspend fun getUserByEmail(email: String): User

    suspend fun createUser(user: User): Pair<Boolean, String>

    suspend fun deleteUser(email: String): Boolean

    suspend fun updateUser(user: User)
}