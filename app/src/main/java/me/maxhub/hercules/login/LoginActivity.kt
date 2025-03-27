package me.maxhub.hercules.login

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import me.maxhub.hercules.MainActivity
import me.maxhub.hercules.api.TokenManager
import me.maxhub.hercules.auth.AuthConfiguration
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.EndSessionRequest
import net.openid.appauth.ResponseTypeValues
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var tokenManager: TokenManager

    @Inject
    lateinit var authService: AuthorizationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = {
                        login()
                    }) {
                        Text("Login")
                    }
                    Button(onClick = {
                        logout()
                    }) {
                        Text("Logout")
                    }
                }
            }
        }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                val ex = AuthorizationException.fromIntent(activityResult.data)
                val result = AuthorizationResponse.fromIntent(activityResult.data!!)

                if (ex != null) {
                    Log.e("Keycloak Auth", "launcher: $ex")
                } else {
                    val tokenRequest = result?.createTokenExchangeRequest()
                    authService.performTokenRequest(tokenRequest!!) { response, tokenEx ->
                        if (tokenEx != null) {
                            Log.e("Keycloak Token ", "token request: $tokenEx")
                        } else {
                            response?.idToken?.let { tokenManager.saveIdToken(it) }
                            response?.accessToken?.let { tokenManager.saveAccessToken(it) }
                            response?.refreshToken?.let { tokenManager.saveRefreshToken(it) }
                            response?.accessTokenExpirationTime?.let {
                                tokenManager.saveTokenExpirationTime(it)
                            }
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
        }

    private fun login() {
        val authRequest = AuthorizationRequest.Builder(
            AuthConfiguration.SERVICE_CONFIG,
            AuthConfiguration.CLIENT_ID,
            ResponseTypeValues.CODE,
            Uri.parse("me.maxhub.hercules://oauth2callback")
        )
            .setScope("openid profile email")
            .build()

        val authorizationRequestIntent = authService.getAuthorizationRequestIntent(authRequest)
        launcher.launch(authorizationRequestIntent)
    }

    private fun logout() {
        val endSessionRequest = EndSessionRequest.Builder(AuthConfiguration.SERVICE_CONFIG)
            .setIdTokenHint(tokenManager.getIdToken())
            .setPostLogoutRedirectUri(Uri.parse("me.maxhub.hercules://oauth2callback"))
            .build()
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_MUTABLE)
        authService.performEndSessionRequest(endSessionRequest, pendingIntent)
        tokenManager.clearTokens()
    }

    override fun onDestroy() {
        super.onDestroy()
        authService.dispose()
    }
}