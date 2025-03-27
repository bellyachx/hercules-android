package me.maxhub.hercules.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    @SerialName("access_token") val accessToken: String,
    @SerialName("expires_in") val expiresIn: Long,
    @SerialName("refresh_token") val refreshToken: String,
    @SerialName("refresh_expires_in") val refreshExpiresIn: Long,
    @SerialName("id_token") val idToken: String,
)
