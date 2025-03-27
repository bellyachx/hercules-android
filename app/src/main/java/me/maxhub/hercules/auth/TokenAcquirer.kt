package me.maxhub.hercules.auth

import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import me.maxhub.hercules.api.TokenAcquirer
import me.maxhub.hercules.api.TokenManager
import me.maxhub.hercules.api.model.TokenRequest
import me.maxhub.hercules.data.AuthGateway
import me.maxhub.hercules.login.LoginActivity
import java.time.Duration
import javax.inject.Inject

class TokenAcquirerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val tokenManager: TokenManager,
    private val authGateway: AuthGateway
) : TokenAcquirer {

    override fun acquire() {
        if (!tokenManager.shouldRefreshToken()) {
            return
        }

        val refreshToken = tokenManager.getRefreshToken()
        if ((refreshToken).isNullOrBlank()) {
            redirectToLoginActivity()
            return
        }

        val tokenRequest = TokenRequest(
            "refresh_token",
            refreshToken,
            AuthConfiguration.CLIENT_ID
        )
        val tokenResponse = runBlocking { authGateway.tokenRequest(tokenRequest) }

        tokenResponse.idToken.let { tokenManager.saveIdToken(it) }
        tokenResponse.accessToken.let { tokenManager.saveAccessToken(it) }
        tokenResponse.refreshToken.let { tokenManager.saveRefreshToken(it) }
        tokenResponse.expiresIn.let {
            val expirationTime = System.currentTimeMillis() + Duration.ofSeconds(it).toMillis()
            tokenManager.saveTokenExpirationTime(expirationTime) }
    }

    private fun redirectToLoginActivity() {
        val loginIntent = Intent(context, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        context.startActivity(loginIntent)
    }
}