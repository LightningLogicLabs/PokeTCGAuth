package com.poketcgdb.data.db

import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database

object UserDB {

    var url: String = ""
    var userName: String = ""
    var password: String = ""

    val database by lazy {
        val dataSource = HikariDataSource()
        dataSource.jdbcUrl = url
        dataSource.username = userName
        dataSource.password = password
        Database.connect(dataSource)
    }
}