package com.poketcgdb.common.utils

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder

object PasswordUtil {

    private const val saltLength = 128 / 8 // 128 bits
    private const val hashLength = 256 / 8 // 256 bits
    private const val parallelism = 1
    private const val memoryInKb = 10 * 1024 // 10 MB
    private const val iterations = 10

    private val passwordEncoder = Argon2PasswordEncoder(
        saltLength,
        hashLength,
        parallelism,
        memoryInKb,
        iterations
    )

    @Throws
    fun hashPassword(password: String) = passwordEncoder.encode(password)

    fun verifyPassword(password: String, hashedPassword: String) = passwordEncoder.matches(password, hashedPassword)
}