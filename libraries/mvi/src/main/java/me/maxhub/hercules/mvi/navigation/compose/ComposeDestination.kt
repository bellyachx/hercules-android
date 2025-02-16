package me.maxhub.hercules.mvi.navigation.compose

import me.maxhub.hercules.mvi.navigation.Destination

interface ComposeDestination : Destination {
    val routeName: String
}

internal object NavigateBack : ComposeDestination {
    override val routeName: String = ""
}