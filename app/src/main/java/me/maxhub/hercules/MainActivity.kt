package me.maxhub.hercules

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import me.maxhub.hercules.widgets.BottomNavItem
import me.maxhub.hercules.widgets.BottomNavigation

class MainActivity : AppCompatActivity() {
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
                        R.string.favorites_bottom_title,
                        Icons.Default.Favorite,
                        "home"
                    ),
                    BottomNavItem(
                        R.string.profile_bottom_title,
                        Icons.Default.AccountCircle,
                        "home"
                    ),
                )
            var selectedItem by remember { mutableStateOf(navigationItems[0]) }

            Column {
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(selectedItem.labelRes),
                            color = Color.Black,
                            fontSize = 24.sp
                        )
                    }
                }
                BottomNavigation(
                    items = navigationItems,
                    selectedItem = selectedItem,
                    onItemSelected = {
                        selectedItem = it
                        // todo enable when will add navigation.
//                        rootNavController.navigate(it.route) {
//                            popUpTo(rootNavController.graph.findStartDestination().id) {
//                                saveState = true
//                            }
//                            launchSingleTop = true
//                            restoreState = true
//                        }
                    }
                )
            }
        }
    }
}
