package com.poketcgdb.data.repository

import com.poketcgdb.data.dao.interfaces.UserDao
import com.poketcgdb.domain.model.User
import com.poketcgdb.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userDao: UserDao
): UserRepository {

    override suspend fun getUserByEmail(email: String): User = userDao.getUserByEmail(email)

    override suspend fun createUser(user: User): Pair<Boolean, String> = userDao.createUser(user)

    override suspend fun deleteUser(email: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: User) {
        TODO("Not yet implemented")
    }
}