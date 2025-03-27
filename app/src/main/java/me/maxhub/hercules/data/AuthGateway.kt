package me.maxhub.hercules.data

import me.maxhub.hercules.api.data.TokenDataSource
import me.maxhub.hercules.api.model.TokenRequest
import me.maxhub.hercules.api.model.TokenResponse
import javax.inject.Inject

interface AuthGateway {
    suspend fun tokenRequest(tokenRequest: TokenRequest): TokenResponse
}

class AuthGatewayImpl @Inject constructor(private val dataSource: TokenDataSource)
    : AuthGateway {
    override suspend fun tokenRequest(tokenRequest: TokenRequest): TokenResponse {
        return dataSource.tokenRequest(tokenRequest)
    }
}
