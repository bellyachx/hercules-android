package me.maxhub.hercules.mvi.flow

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.MutableStateFlow

private class SavedStateHandlerStateFlow<T>(
    private val key: String,
    private val savedStateHandle: SavedStateHandle,
    private val initialValue: T,
    private val actualFlow: MutableStateFlow<T> = MutableStateFlow(
        savedStateHandle[key] ?: initialValue
    ),
) : MutableStateFlow<T> by actualFlow {
    override var value: T
        set(value) {
            savedStateHandle[key] = value
            actualFlow.value = value
        }
        get() = actualFlow.value
}

fun <T> SavedStateHandle.asMutableStateFlow(
    key: String,
    initialValue: T
): MutableStateFlow<T> {
    return SavedStateHandlerStateFlow(key, this, initialValue)
}