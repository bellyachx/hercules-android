package me.maxhub.hercules.api

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager,
    private val tokenAcquirer: TokenAcquirer
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        tokenAcquirer.acquire()
        val accessToken = tokenManager.getAccessToken()
        val newRequest = accessToken?.let { token ->
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } ?: originalRequest

        return try{
            chain.proceed(newRequest)
        } catch (e: UnauthorizedException) {
            // todo redirect to login activity if a 401 HTTP response is received
            throw IOException("Unauthorized exception")
        }
    }
}