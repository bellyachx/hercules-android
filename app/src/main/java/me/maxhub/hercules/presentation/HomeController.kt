package me.maxhub.hercules.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.maxhub.hercules.mvi.UIController
import me.maxhub.hercules.mvi.flow.asMutableStateFlow
import me.maxhub.hercules.mvi.navigation.Navigator
import me.maxhub.hercules.navigation.home.HomeDestination
import javax.inject.Inject

interface HomeController : UIController<HomeState, HomeUIEvent>

@HiltViewModel
class HomeControllerImpl @Inject constructor(
    private val navigator: Navigator<HomeDestination>,
    savedStateHandle: SavedStateHandle
) : ViewModel(), HomeController {
    override val state = savedStateHandle.asMutableStateFlow(
        "home",
        HomeState()
    )
}
