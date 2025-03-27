package me.maxhub.hercules.auth

import android.net.Uri
import net.openid.appauth.AuthorizationServiceConfiguration

object AuthConfiguration {

    const val CLIENT_ID = "hercules_pkce"

    private const val AUTH_URL = "https://kc.maxhub.me/realms/hercules/protocol/openid-connect/auth"
    private const val TOKEN_URL = "https://kc.maxhub.me/realms/hercules/protocol/openid-connect/token"
    private const val END_SESSION_URL =
        "https://kc.maxhub.me/realms/hercules/protocol/openid-connect/logout"

    val SERVICE_CONFIG = AuthorizationServiceConfiguration(
        Uri.parse(AUTH_URL),
        Uri.parse(TOKEN_URL),
        null,
        Uri.parse(END_SESSION_URL)
    )
}
