package me.maxhub.hercules

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.maxhub.hercules.api.TokenCache
import me.maxhub.hercules.login.LoginActivity
import me.maxhub.hercules.mvi.navigation.ViewModelNavigator
import me.maxhub.hercules.navigation.MainDestination
import me.maxhub.hercules.navigation.MainNavHost
import me.maxhub.hercules.navigation.home.HomeDestination
import me.maxhub.hercules.widgets.BottomNavItem
import me.maxhub.hercules.widgets.BottomNavigation
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var homeNavigator: ViewModelNavigator<HomeDestination>

    @Inject
    lateinit var tokenCache: TokenCache

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val content: View = findViewById(android.R.id.content)
        val preDrawListener = object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                val viewTreeObserver = content.viewTreeObserver
                if (viewTreeObserver.isAlive) {
                    viewTreeObserver.removeOnPreDrawListener(this)
                }

                if (tokenCache.getAccessToken().isNullOrBlank() &&
                    tokenCache.getRefreshToken().isNullOrBlank()) {
                    val intent = Intent(
                        this@MainActivity,
                        LoginActivity::class.java
                    ).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    startActivity(intent)
                    finish()
                    return false
                }
                return true
            }
        }
        content.viewTreeObserver.addOnPreDrawListener(preDrawListener)
        setContent {
            val rootNavController = rememberNavController()

            val navigationItems =
                listOf(
                    BottomNavItem(
                        R.string.home_bottom_title,
                        Icons.Default.Home,
                        MainDestination.Home.routeName
                    ),
                    BottomNavItem(
                        R.string.favorites_bottom_title,
                        Icons.Default.Favorite,
                        MainDestination.Favorites.routeName
                    ),
                    BottomNavItem(
                        R.string.profile_bottom_title,
                        Icons.Default.AccountCircle,
                        MainDestination.Profile.routeName
                    ),
                )
            var selectedItem by remember { mutableStateOf(navigationItems[0]) }

            Column {
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                ) {
                    MainNavHost(
                        rootNavController,
                        MainDestination.Home,
                        homeNavigator
                    )
                }
                BottomNavigation(
                    items = navigationItems,
                    selectedItem = selectedItem,
                    onItemSelected = {
                        selectedItem = it
                        rootNavController.navigate(it.route) {
                            popUpTo(rootNavController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}
