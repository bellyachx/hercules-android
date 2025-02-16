 package me.maxhub.hercules.navigation

 import androidx.compose.foundation.layout.Box
 import androidx.compose.foundation.layout.fillMaxSize
 import androidx.compose.material.Text
 import androidx.compose.runtime.Composable
 import androidx.compose.runtime.LaunchedEffect
 import androidx.compose.ui.Alignment
 import androidx.compose.ui.Modifier
 import androidx.navigation.NavController
 import androidx.navigation.NavGraphBuilder
 import androidx.navigation.compose.composable
 import androidx.navigation.compose.rememberNavController
 import me.maxhub.hercules.mvi.navigation.ViewModelNavigator
 import me.maxhub.hercules.mvi.navigation.compose.ComposeDestination
 import me.maxhub.hercules.navigation.home.HomeDestination
 import me.maxhub.hercules.navigation.home.HomeNavControllerAdapter
 import me.maxhub.hercules.navigation.home.HomeNavHost
 import java.io.Serializable

 sealed class MainDestination : ComposeDestination, Serializable {
     object Home : MainDestination() {
         override val routeName: String = "home"
         fun register(
             navGraph: NavGraphBuilder,
             navigator: ViewModelNavigator<HomeDestination>,
             parentNavController: NavController,
         ) {
             navGraph.composable(route = routeName, content = {
                 val navController = rememberNavController()
                 LaunchedEffect(key1 = Unit) {
                     HomeNavControllerAdapter(
                         navController = navController,
                         navigator = navigator,
                         closeWindow = { parentNavController.navigateUp() },
                     ).init(this)
                 }
                 HomeNavHost(navController, HomeDestination.Home)
             })
         }
     }

     object Favorites : MainDestination() {
         override val routeName: String = "favorites"
         fun register(
             navGraph: NavGraphBuilder,
         ) {
             navGraph.composable(route = routeName, content = {
                 NavigationScreen("Favorites")
             })
         }
     }

     object Profile : MainDestination() {
         override val routeName: String = "profile"
         fun register(
             navGraph: NavGraphBuilder,
         ) {
             navGraph.composable(route = routeName, content = {
                 NavigationScreen("Profile")
             })
         }
     }
 }

 @Composable
 fun NavigationScreen(text: String) {
     Box(
         modifier = Modifier.fillMaxSize(),
         contentAlignment = Alignment.Center
     ) {
         Text(text)
     }
 }
