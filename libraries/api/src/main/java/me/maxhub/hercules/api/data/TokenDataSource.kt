package me.maxhub.hercules.api.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.maxhub.hercules.api.AuthService
import me.maxhub.hercules.api.model.TokenRequest
import me.maxhub.hercules.api.model.TokenResponse
import javax.inject.Inject

interface TokenDataSource {
    suspend fun tokenRequest(tokenRequest: TokenRequest): TokenResponse
}

class TokenDataSourceImpl @Inject constructor(private val service: AuthService)
    : TokenDataSource {
    override suspend fun tokenRequest(tokenRequest: TokenRequest): TokenResponse {
        val requestParams = mapOf(
            "grant_type" to tokenRequest.grantType,
            "refresh_token" to tokenRequest.refreshToken,
            "client_id" to tokenRequest.clientId
        )
        return withContext(Dispatchers.IO) { service.refreshTokens(requestParams)}
    }
}
