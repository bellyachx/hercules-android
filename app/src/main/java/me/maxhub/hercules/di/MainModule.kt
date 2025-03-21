package me.maxhub.hercules.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import me.maxhub.hercules.data.ExerciseGateway
import me.maxhub.hercules.data.ExerciseGatewayImpl
import me.maxhub.hercules.mvi.navigation.Navigator
import me.maxhub.hercules.mvi.navigation.ViewModelNavigator
import me.maxhub.hercules.navigation.MainDestination
import me.maxhub.hercules.navigation.home.HomeDestination

@Module
@InstallIn(ViewModelComponent::class)
abstract class MainModule {
    @Binds
    abstract fun bindNavigator(
        impl: ViewModelNavigator<MainDestination>
    ): Navigator<MainDestination>

    @Binds
    abstract fun bindHomeNavigator(
        impl: ViewModelNavigator<HomeDestination>
    ): Navigator<HomeDestination>

    @Binds
    abstract fun bindExerciseGateway(
        impl: ExerciseGatewayImpl
    ): ExerciseGateway
}