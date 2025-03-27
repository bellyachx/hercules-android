package me.maxhub.hercules.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.openid.appauth.AuthorizationService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainProvidesModule {

    @Provides
    @Singleton
    fun provideAppAuth(@ApplicationContext context: Context) = AuthorizationService(context)
}