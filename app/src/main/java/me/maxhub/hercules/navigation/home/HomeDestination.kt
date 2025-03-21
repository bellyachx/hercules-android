package me.maxhub.hercules.navigation.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import me.maxhub.hercules.mvi.navigation.compose.ComposeDestination
import me.maxhub.hercules.presentation.ExerciseScreen
import me.maxhub.hercules.presentation.HomeScreen
import java.io.Serializable

sealed class HomeDestination : ComposeDestination, Serializable {
    object Home : HomeDestination() {
        override val routeName: String = "home"
        fun register(
            navGraph: NavGraphBuilder,
        ) {
            navGraph.composable(route = routeName, content = {
                HomeScreen()
            })
        }
    }

    object ExerciseDetails : HomeDestination() {
        override val routeName: String = "exercise"
        fun register(
            navGraph: NavGraphBuilder,
        ) {
            navGraph.composable(route = routeName, content = {
                ExerciseScreen()
            })
        }
    }
}