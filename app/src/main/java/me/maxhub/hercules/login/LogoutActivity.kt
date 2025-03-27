package me.maxhub.hercules.login

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import me.maxhub.hercules.api.TokenManager
import net.openid.appauth.AuthorizationService
import javax.inject.Inject

@AndroidEntryPoint
class LogoutActivity : AppCompatActivity() {

    private lateinit var authService: AuthorizationService

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        authService = AuthorizationService(this)

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
                        logout()
                    }) {
                        Text("Logout")
                    }
                }
            }
        }
    }

    private fun logout() {
//        val endSessionRequest = EndSessionRequest.Builder(AuthConfiguration.SERVICE_CONFIG)
//            .setIdTokenHint(tokenManager.getIdToken())
//            .setPostLogoutRedirectUri(Uri.parse("me.maxhub.hercules://oauth2callback"))
//            .build()
//        val intent = Intent(this, MainActivity::class.java)
//        val pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_MUTABLE)
//        authService.performEndSessionRequest(endSessionRequest, pendingIntent)
//        tokenManager.clearTokens()
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        authService.dispose()
//    }
}