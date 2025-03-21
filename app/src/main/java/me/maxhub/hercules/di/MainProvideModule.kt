package me.maxhub.hercules.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.maxhub.hercules.api.TokenCache
import me.maxhub.hercules.api.TokenManager
import me.maxhub.hercules.api.TokenManagerImpl
import me.maxhub.hercules.login.RefreshCallback
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainProvideModule {
    @Provides
    @Singleton
    fun provideTokenManager(tokenCache: TokenCache, refreshCallback: RefreshCallback): TokenManager =
        TokenManagerImpl(tokenCache, refreshCallback)
}
