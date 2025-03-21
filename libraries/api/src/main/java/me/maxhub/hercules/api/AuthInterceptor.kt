package me.maxhub.hercules.api

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val accessToken = tokenManager.getAccessToken()
        if (!accessToken.isNullOrBlank()) {
            request = request.newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
        }

        return try {
            chain.proceed(request)
        } catch (e: UnauthorizedException) {
            synchronized(this) {
                runBlocking { tokenManager.refreshToken() }
                val newAccessToken = tokenManager.getAccessToken()
                if (!newAccessToken.isNullOrBlank()) {
                    request = request.newBuilder()
                        .addHeader("Authorization", "Bearer $newAccessToken")
                        .build()
                    chain.proceed(request)
                } else {
                    throw IOException("Unable to refresh token!")
                }
            }
        }
    }
}