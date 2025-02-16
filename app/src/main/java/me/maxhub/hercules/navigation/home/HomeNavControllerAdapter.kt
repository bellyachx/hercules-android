package me.maxhub.hercules.navigation.home

import androidx.navigation.NavController
import me.maxhub.hercules.mvi.navigation.ViewModelNavigator
import me.maxhub.hercules.mvi.navigation.compose.ComposeDestination
import me.maxhub.hercules.mvi.navigation.compose.NavControllerAdapter

class HomeNavControllerAdapter(
    navController: NavController,
    navigator: ViewModelNavigator<HomeDestination>,
    private val closeWindow: () -> Unit,
) : NavControllerAdapter<HomeDestination>(navController, navigator) {
    override fun navigateUp() {
        if (navController.previousBackStackEntry == null) {
            closeWindow()
        } else {
            super.navigateUp()
        }
    }

    override fun navigate(destination: ComposeDestination) {
        when (destination) {
            else -> {
                super.navigate(destination)
            }
        }
    }
}
