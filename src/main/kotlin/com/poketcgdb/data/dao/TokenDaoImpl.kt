package com.poketcgdb.data.dao

import com.poketcgdb.common.utils.DateUtil
import com.poketcgdb.data.dao.interfaces.TokenDao
import com.poketcgdb.data.db.TokenDB
import java.util.*

class TokenDaoImpl(
    private val tokenDB: TokenDB
): TokenDao {

    override suspend fun storeAccessClaim(accessUUID: String, userId: String, expiryDate: Date) = async {
        tokenDB.jedis.set(accessUUID, userId)
        tokenDB.jedis.expire(accessUUID, DateUtil.getSecondsRemainingUntilDate(expiryDate))
        Unit
    }

    override suspend fun storeRefreshClaim(refreshUUID: String, userId: String, expiryDate: Date) = async {
        tokenDB.jedis.set(refreshUUID, userId)
        tokenDB.jedis.expire(refreshUUID, DateUtil.getSecondsRemainingUntilDate(expiryDate))
        Unit
    }

    override suspend fun doesAccessClaimExist(accessUUID: String): Boolean = async {
        tokenDB.jedis.get(accessUUID).isNullOrEmpty()
    }

    override suspend fun doesRefreshClaimExist(refreshUUID: String): Boolean = async {
        tokenDB.jedis.get(refreshUUID).isNullOrEmpty()
    }

    override suspend fun revokeAccessToken(accessUUID: String): Boolean = async {
        true
    }

    override suspend fun revokeRefreshToken(refreshUUID: String): Boolean = async {
        true
    }
}