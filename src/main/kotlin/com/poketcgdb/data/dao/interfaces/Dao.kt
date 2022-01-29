package com.poketcgdb.data.dao.interfaces

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.statements.api.PreparedStatementApi
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction

interface Dao {

    suspend fun <T> async(query: String, block: (statement: PreparedStatementApi) -> T): T = withContext(Dispatchers.IO) {
        transaction {
            val conn = TransactionManager.current().connection
            val statement = conn.prepareStatement(query, false)
            block(statement)
        }
    }

    suspend fun <T> async(block:() -> T): T = withContext(Dispatchers.IO) {
        block()
    }
}