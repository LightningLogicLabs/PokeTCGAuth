package com.poketcgdb.data.db

import redis.clients.jedis.JedisPooled
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

object TokenDB {

    var url: String = ""

    val jedis by lazy {
        JedisPooled(url)
    }

    private fun createSSLContext(): SSLContext {
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, arrayOf(createTrustManager()), SecureRandom())
        return sslContext
    }

    private fun createTrustManager() = object: X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

        override fun getAcceptedIssuers() = arrayOf<X509Certificate>()
    }
}