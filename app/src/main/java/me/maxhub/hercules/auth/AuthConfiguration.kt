package me.maxhub.hercules.auth

import android.net.Uri
import net.openid.appauth.AuthorizationServiceConfiguration

object AuthConfiguration {

    val CLIENT_ID = "hercules_pkce"
    val AUTH_URL = "https://kc.maxhub.me/realms/hercules/protocol/openid-connect/auth"
    val TOKEN_URL = "https://kc.maxhub.me/realms/hercules/protocol/openid-connect/token"

    val SERVICE_CONFIG = AuthorizationServiceConfiguration(
        Uri.parse(AUTH_URL),
        Uri.parse(TOKEN_URL)
    )
}
