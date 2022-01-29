package com.poketcgdb.domain.repository

import java.util.*

interface TokenRepository {

    suspend fun storeAccessClaim(accessUUID: String, userId: String, expiresAt: Date)

    suspend fun storeRefreshClaim(refreshUUID: String, userId: String, expiresAt: Date)

    suspend fun revokeAccessToken(accessUUID: String): Boolean

    suspend fun revokeRefreshToken(refreshUUID: String): Boolean

    suspend fun doesAccessClaimExist(accessUUID: String): Boolean

    suspend fun doesRefreshClaimExist(refreshUUID: String): Boolean
}