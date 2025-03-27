package me.maxhub.hercules

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsIgnoringVisibility
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
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

    @OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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


            val currentBackStackEntry = rootNavController.currentBackStackEntryAsState()
            val currentDestination = currentBackStackEntry.value?.destination?.route

            val title = when (currentDestination) {
                MainDestination.Home.routeName -> stringResource(R.string.home_bottom_title)
                MainDestination.Favorites.routeName -> stringResource(R.string.favorites_bottom_title)
                MainDestination.Profile.routeName -> stringResource(R.string.profile_bottom_title)
                else -> stringResource(R.string.app_name)
            }

            Scaffold(
                contentWindowInsets = WindowInsets.systemBarsIgnoringVisibility,
                topBar = {
                    CenterAlignedTopAppBar(
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = { Text(text = title) },
                    )
                }
            ) { innerPadding ->
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
}
