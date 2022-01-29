package com.poketcgdb.data.model

import com.poketcgdb.domain.model.User
import java.time.LocalDate

data class DBUser(
    val id: Int = 0,
    val email: String = "",
    val password: String = "",
    val dateCreated: LocalDate = LocalDate.now(),
    val isActive: Boolean = true
): DBModel<DBUser, User> {

    override fun mapToDomain() = User(
        email = this.email,
        password = this.password,
        dateCreated = this.dateCreated,
        isActive = this.isActive
    )
}