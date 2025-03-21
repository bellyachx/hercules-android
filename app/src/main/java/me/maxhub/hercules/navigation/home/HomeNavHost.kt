package me.maxhub.hercules.navigation.home

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
internal fun HomeNavHost(
    navController: NavHostController,
    startDestination: HomeDestination,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.routeName,
        enterTransition = {
            fadeIn(initialAlpha = 0.0f)
        },
        exitTransition = {
            fadeOut(targetAlpha = 0.0f)
        },
        popEnterTransition = {
            fadeIn(initialAlpha = 0.0f)
        },
        popExitTransition = {
            fadeOut(targetAlpha = 0.0f)
        },
    ) {
        HomeDestination.Home.register(this)
        HomeDestination.ExerciseDetails.register(this)
    }
}