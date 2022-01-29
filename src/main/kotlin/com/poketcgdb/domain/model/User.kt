package com.poketcgdb.domain.model

import java.time.LocalDate

data class User(
    val email: String = "",
    val password: String = "",
    val dateCreated: LocalDate = LocalDate.now(),
    val isActive: Boolean = true
)