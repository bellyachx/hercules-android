package me.maxhub.hercules.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import me.maxhub.hercules.mvi.UIEvent
import me.maxhub.hercules.mvi.UIState
import me.maxhub.hercules.navigation.NavigationScreen

sealed class HomeUIEvent : UIEvent {
    // todo add events
}

class HomeState(
    // todo add state
) : UIState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    controller: HomeController = hiltViewModel<HomeControllerImpl>(),
) {
    NavigationScreen("Home!")
}