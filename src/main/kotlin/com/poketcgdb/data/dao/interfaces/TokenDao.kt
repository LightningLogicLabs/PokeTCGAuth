package com.poketcgdb.data.dao.interfaces

import java.util.Date

interface TokenDao: Dao {

    suspend fun storeAccessClaim(accessUUID: String, userId: String, expiryDate: Date)

    suspend fun storeRefreshClaim(refreshUUID: String, userId: String, expiryDate: Date)

    suspend fun doesAccessClaimExist(accessUUID: String): Boolean

    suspend fun doesRefreshClaimExist(refreshUUID: String): Boolean

    suspend fun revokeAccessToken(accessUUID: String): Boolean

    suspend fun revokeRefreshToken(refreshUUID: String): Boolean
}