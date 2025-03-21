package me.maxhub.hercules.api

import okhttp3.Interceptor
import okhttp3.Response

class ErrorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if (!response.isSuccessful) {
            val responseCode = response.code
            if (responseCode == 401) {
                throw UnauthorizedException()
            }
        }
        return response
    }
}