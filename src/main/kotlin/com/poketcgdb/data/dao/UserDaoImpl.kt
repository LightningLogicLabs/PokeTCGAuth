package com.poketcgdb.data.dao

import com.poketcgdb.data.dao.interfaces.UserDao
import com.poketcgdb.data.model.DBUser
import com.poketcgdb.domain.model.User
import org.jetbrains.exposed.sql.BooleanColumnType
import org.jetbrains.exposed.sql.VarCharColumnType
import org.jetbrains.exposed.sql.javatime.JavaLocalDateColumnType
import org.postgresql.util.PSQLException
import java.time.LocalDate

class UserDaoImpl: UserDao {

    override suspend fun getUserByEmail(email: String): User = async(
        query = "select * from accounts.\"user\" where email = ?"
    ) {
        it.fillParameters(listOf(Pair(VarCharColumnType(), email.lowercase())))

        val resultSet = it.executeQuery()

        var accountId = 0
        var accountEmail = ""
        var accountPassword = ""
        var dateCreated = LocalDate.now()
        var isActive = true

        while (resultSet.next()) {
            accountId = resultSet.getInt("id")
            accountEmail = resultSet.getString("email")
            accountPassword = resultSet.getString("password")
            dateCreated = resultSet.getDate("date_created").toLocalDate()
            isActive = resultSet.getBoolean("is_active")
        }

        DBUser(
            id = accountId,
            email = accountEmail,
            password = accountPassword,
            dateCreated = dateCreated,
            isActive = isActive
        ).mapToDomain()
    }

    override suspend fun createUser(user: User) = async(
        query = "INSERT INTO accounts.\"user\"\n" +
                "(email, \"password\", date_created, is_active)\n" +
                "VALUES(?, ?, ?, ?);\n"
    ) {
        it.fillParameters(listOf(
            Pair(VarCharColumnType(), user.email),
            Pair(VarCharColumnType(), user.password),
            Pair(JavaLocalDateColumnType(), user.dateCreated),
            Pair(BooleanColumnType(), user.isActive)
        ))

        try {
            it.executeUpdate()
            Pair(true, "")
        } catch(e: PSQLException) {
            Pair(false, e.serverErrorMessage?.message ?: "")
        }
    }
}