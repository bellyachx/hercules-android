package me.maxhub.hercules.api

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.maxhub.hercules.api.data.ExerciseDataSource
import me.maxhub.hercules.api.data.ExerciseDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {
    @Binds
    @Singleton
    fun bindExerciseDataSource(
        impl: ExerciseDataSourceImpl
    ): ExerciseDataSource

    @Binds
    @Singleton
    fun bindTokenCache(
        impl: TokenCacheImpl
    ): TokenCache
}