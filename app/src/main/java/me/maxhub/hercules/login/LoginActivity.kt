package me.maxhub.hercules.login

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
import me.maxhub.hercules.api.TokenCache
import me.maxhub.hercules.auth.AuthConfiguration
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.ResponseTypeValues
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var authService: AuthorizationService

    @Inject
    lateinit var tokenCache: TokenCache

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        authService = AuthorizationService(this)

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
                    Button(onClick = { }) {
                        Text("Logout")
                    }
                }
            }
        }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val ex = AuthorizationException.fromIntent(it.data)
                val result = AuthorizationResponse.fromIntent(it.data!!)

                if (ex != null) {
                    Log.e("Keycloak Auth", "launcher: $ex")
                } else {
                    val tokenRequest = result?.createTokenExchangeRequest()
                    authService.performTokenRequest(tokenRequest!!) { response, tokenEx ->
                        if (tokenEx != null) {
                            Log.e("Keycloak Token ", "token request: $tokenEx")
                        } else {
                            tokenCache.saveTokens(response?.accessToken!!, response.refreshToken!!)

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

    override fun onDestroy() {
        super.onDestroy()
        authService.dispose()
    }
}