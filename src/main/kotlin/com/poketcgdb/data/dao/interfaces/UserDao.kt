package com.poketcgdb.data.dao.interfaces

import com.poketcgdb.domain.model.User

interface UserDao: Dao {

    suspend fun getUserByEmail(email: String): User

    suspend fun createUser(user: User): Pair<Boolean, String>
}