package me.maxhub.hercules.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.maxhub.hercules.api.TokenAcquirer
import me.maxhub.hercules.auth.TokenAcquirerImpl
import me.maxhub.hercules.data.AuthGateway
import me.maxhub.hercules.data.AuthGatewayImpl

@Module
@InstallIn(SingletonComponent::class)
interface MainModuleSingleton {
    @Binds
    fun bindTokenAcquirer(impl: TokenAcquirerImpl): TokenAcquirer

    @Binds
    fun bindAuthGateway(impl: AuthGatewayImpl): AuthGateway
}