package com.poketcgdb.data.repository

import com.poketcgdb.data.dao.interfaces.TokenDao
import com.poketcgdb.domain.repository.TokenRepository
import java.util.*

class TokenRepositoryImpl(
    private val tokenDao: TokenDao
): TokenRepository {

    override suspend fun storeAccessClaim(accessUUID: String, userId: String, expiresAt: Date) {
        tokenDao.storeAccessClaim(accessUUID, userId, expiresAt)
    }

    override suspend fun storeRefreshClaim(refreshUUID: String, userId: String, expiresAt: Date) {
        tokenDao.storeRefreshClaim(refreshUUID, userId, expiresAt)
    }

    override suspend fun doesAccessClaimExist(accessUUID: String): Boolean = tokenDao.doesAccessClaimExist(accessUUID)

    override suspend fun doesRefreshClaimExist(refreshUUID: String): Boolean = tokenDao.doesRefreshClaimExist(refreshUUID)

    override suspend fun revokeAccessToken(accessUUID: String): Boolean = tokenDao.revokeAccessToken(accessUUID)

    override suspend fun revokeRefreshToken(refreshUUID: String): Boolean = tokenDao.revokeRefreshToken(refreshUUID)
}