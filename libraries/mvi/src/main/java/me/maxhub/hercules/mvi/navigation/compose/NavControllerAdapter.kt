package me.maxhub.hercules.mvi.navigation.compose

import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import me.maxhub.hercules.mvi.navigation.ViewModelNavigator

open class NavControllerAdapter<R : ComposeDestination>(
    protected val navController: NavController,
    protected open val navigator: ViewModelNavigator<*>
) {
    fun init(coroutineScope: CoroutineScope) {
        navigator.destination.onEach { destination ->
            if (destination == NavigateBack) {
                navigateUp()
            } else {
                navigate(destination)
            }
        }.launchIn(coroutineScope)
    }

    open fun navigate(destination: ComposeDestination) {
        navController.navigate(route = destination.routeName)
    }

    open fun navigateUp() {
        navController.navigateUp()
    }
}