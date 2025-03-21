package me.maxhub.hercules.api

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface TokenCache {
    fun saveTokens(accessToken: String, refreshToken: String)
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun clearTokens()
}

class TokenCacheImpl @Inject constructor(@ApplicationContext context: Context) :
    TokenCache {

    companion object {
        private const val PREFS_NAME = "encrypted_prefs"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
    }

    private val sharedPreferences by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            PREFS_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    private var accessToken: String? = null

    override fun saveTokens(accessToken: String, refreshToken: String) {
        this.accessToken = accessToken
        sharedPreferences.edit().putString(KEY_REFRESH_TOKEN, refreshToken).apply()
    }

    override fun getAccessToken(): String? {
        return this.accessToken
    }

    override fun getRefreshToken(): String? {
        return sharedPreferences.getString(KEY_REFRESH_TOKEN, null)
    }

    override fun clearTokens() {
        sharedPreferences.edit().remove(KEY_REFRESH_TOKEN).apply()
    }
}
