package me.maxhub.hercules.mvi.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.maxhub.hercules.mvi.navigation.compose.ComposeDestination
import me.maxhub.hercules.mvi.navigation.compose.NavigateBack
import javax.inject.Inject

@ActivityRetainedScoped
class ViewModelNavigator<R : ComposeDestination> @Inject constructor() : ViewModel(), Navigator<R> {
    internal val destination = MutableSharedFlow<ComposeDestination>()
    override val resultFlow = MutableStateFlow<NavigationPayload?>(null)

    private val inputMap: MutableMap<R, NavigationPayload> = mutableMapOf()

    override fun navigate(route: R, payload: NavigationPayload?) {
        viewModelScope.launch {
            payload?.let { inputMap[route] = it }
            destination.emit(route)
        }
    }

    override fun navigateUp(result: NavigationPayload?) {
        viewModelScope.launch {
            destination.emit(NavigateBack)
            resultFlow.value = result
        }
    }

    override fun <T : NavigationPayload> readInput(inputClass: Class<T>, route: R): T? {
        val payload = inputMap[route]
        return if (inputClass.isInstance(payload)) {
            inputClass.cast(payload)
        } else {
            null
        }
    }

    override fun <T : NavigationPayload> requireInput(inputClass: Class<T>, route: R): T {
        val payload = inputMap[route]
        return inputClass.cast(payload)!!
    }
}