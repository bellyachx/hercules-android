package me.maxhub.hercules.mvi.navigation

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.Flow
import java.io.Serializable

@Stable
interface Navigator<R : Destination> {
    fun navigate(route: R, payload: NavigationPayload? = null)

    fun navigateUp(result: NavigationPayload? = null)

    val resultFlow: Flow<NavigationPayload?>

    fun <T : NavigationPayload> readInput(inputClass: Class<T>, route: R): T?
    fun <T : NavigationPayload> requireInput(inputClass: Class<T>, route: R): T
}

interface Destination

interface NavigationPayload : Serializable