package me.maxhub.hercules.login

import android.content.Context
import android.content.Intent
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import me.maxhub.hercules.api.TokenCache
import me.maxhub.hercules.auth.AuthConfiguration
import net.openid.appauth.AuthorizationException
import net.openid.appauth.GrantTypeValues
import net.openid.appauth.TokenRequest
import javax.inject.Inject

class RefreshCallback @Inject constructor(
    @ApplicationContext private val context: Context
) : Function1<TokenCache, Unit> {
    private val authService by lazy {
        net.openid.appauth.AuthorizationService(context)
    }

    override fun invoke(tokenCache: TokenCache) {
        if (tokenCache.getRefreshToken().isNullOrBlank()) {
            redirectToLoginActivity()
            return
        }
        val tokenRequest = TokenRequest.Builder(
            AuthConfiguration.SERVICE_CONFIG,
            AuthConfiguration.CLIENT_ID
        )
            .setRefreshToken(tokenCache.getRefreshToken())
            .setGrantType(GrantTypeValues.REFRESH_TOKEN)
            .build()
        authService.performTokenRequest(tokenRequest) { response, ex ->
            if (ex != null) {
                Log.e("Keycloak Token ", "token request: $ex")
                if (ex == AuthorizationException.TokenRequestErrors.INVALID_GRANT) {
                    redirectToLoginActivity()
                }
            } else {
                tokenCache.saveTokens(response?.accessToken!!, response.refreshToken!!)
            }
        }
    }

    private fun redirectToLoginActivity() {
        val loginIntent = Intent(context, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        context.startActivity(loginIntent)
    }
}
