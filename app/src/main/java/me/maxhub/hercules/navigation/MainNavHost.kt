package me.maxhub.hercules.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import me.maxhub.hercules.mvi.navigation.ViewModelNavigator
import me.maxhub.hercules.navigation.home.HomeDestination

@Composable
internal fun MainNavHost(
    navController: NavHostController,
    startDestination: MainDestination,
    homeNavigator: ViewModelNavigator<HomeDestination>,
    modifier: Modifier = Modifier,
) {

    NavHost(
        navController = navController,
        startDestination = startDestination.routeName,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth }
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth }
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth }
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth }
            )
        },
        modifier = modifier,
    ) {
        MainDestination.Home.register(this, homeNavigator, navController)
        MainDestination.Favorites.register(this)
        MainDestination.Profile.register(this)
    }
}