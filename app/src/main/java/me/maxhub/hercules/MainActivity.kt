package me.maxhub.hercules

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import me.maxhub.hercules.widgets.BottomNavItem
import me.maxhub.hercules.widgets.BottomNavigation

class MainActivity : ComponentActivity() {
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
                        "home"
                    ),
                    BottomNavItem(
                        R.string.home_bottom_title,
                        Icons.Default.Favorite,
                        "favorite"
                    ),
                    BottomNavItem(
                        R.string.home_bottom_title,
                        Icons.Default.AccountCircle,
                        "profile"
                    ),
                )
            var selectedItem by remember { mutableStateOf(navigationItems[0]) }

            Column {
                Surface(Modifier.weight(1f)) {
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
//    HerculesTheme {
//        Greeting("Android")
//    }
}