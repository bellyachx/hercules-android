package me.maxhub.hercules.mvi

import kotlinx.coroutines.flow.StateFlow

interface UIController<S : UIState, E : UIEvent> {
    val state: StateFlow<S>

    fun onEvent(event: E) {}
}
