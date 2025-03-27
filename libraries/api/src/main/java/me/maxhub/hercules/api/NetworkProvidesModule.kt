package me.maxhub.hercules.api

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkProvidesModule {

    @Provides
    @Singleton
    internal fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Provides
    @Singleton
    @Named("Default")
    internal fun provideRetrofit(json: Json, okHttpClient: OkHttpClient,
                                 authInterceptor: AuthInterceptor): Retrofit {
        val httpClient = okHttpClient.newBuilder()
            .addInterceptor(authInterceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl("https://be.hercules.maxhub.me/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(httpClient)
            .build()
    }

    @Provides
    @Singleton
    @Named("Auth")
    fun provideAuthRetrofit(json: Json, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://kc.maxhub.me/realms/hercules/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    internal fun provideOkHttClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ErrorInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @Singleton
    internal fun provideAuthInterceptor(
        tokenManager: TokenManager,
        tokenAcquirer: TokenAcquirer
    ): AuthInterceptor = AuthInterceptor(tokenManager, tokenAcquirer)

    @Provides
    @Singleton
    internal fun provideApiService(@Named("Default") retrofit: Retrofit): HerculesService =
        retrofit.create(HerculesService::class.java)

    @Provides
    @Singleton
    internal fun provideAuthService(@Named("Auth") retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    internal fun provideTokenManager(@ApplicationContext context: Context): TokenManager =
        TokenManager(context)
}
