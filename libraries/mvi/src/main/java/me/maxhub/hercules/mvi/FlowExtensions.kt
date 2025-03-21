package me.maxhub.hercules.mvi

import kotlinx.coroutines.flow.MutableStateFlow

fun <T> MutableStateFlow<T>.set(bock: T.() -> T) {
    this.value = this.value.bock()
}