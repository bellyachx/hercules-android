package me.maxhub.hercules.api.model

import kotlinx.serialization.Serializable

@Serializable
data class TokenRequest(
    val grantType: String,
    val refreshToken: String,
    val clientId: String
)
