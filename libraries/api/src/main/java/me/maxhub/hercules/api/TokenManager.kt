package me.maxhub.hercules.api

import javax.inject.Inject

interface TokenManager {
    fun getAccessToken(): String?
    suspend fun refreshToken()
}

class TokenManagerImpl @Inject constructor(
    private val tokenCache: TokenCache,
    private val refreshCallback: (TokenCache) -> Unit
) : TokenManager {
    override fun getAccessToken(): String? {
        return tokenCache.getAccessToken()
    }

    override suspend fun refreshToken() {
        refreshCallback(tokenCache)
    }
}
