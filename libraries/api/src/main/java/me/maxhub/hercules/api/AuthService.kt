package me.maxhub.hercules.api

import me.maxhub.hercules.api.model.TokenResponse
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthService {

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("protocol/openid-connect/token")
    suspend fun refreshTokens(@FieldMap requestParameters: Map<String, String>) : TokenResponse
}
