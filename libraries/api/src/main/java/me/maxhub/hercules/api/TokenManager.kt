package me.maxhub.hercules.api

import android.content.Context
import javax.inject.Inject

class TokenManager @Inject constructor(
    context: Context,
) {
    companion object {
        private const val PREF_NAME = "TokenPrefs"
        private const val ID_TOKEN = "id_token"
        private const val ACCESS_TOKEN = "access_token"
        private const val REFRESH_TOKEN = "refresh_token"
        private const val TOKEN_EXPIRATION = "token_expiration"
    }
    private val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun getIdToken(): String? {
        return sharedPreferences.getString(ID_TOKEN, null)
    }

    fun saveIdToken(token: String) {
        sharedPreferences.edit().putString(ID_TOKEN, token).apply()
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString(ACCESS_TOKEN, null)
    }

    fun saveAccessToken(token: String) {
        sharedPreferences.edit().putString(ACCESS_TOKEN, token).apply()
    }

    fun getRefreshToken(): String? {
        return sharedPreferences.getString(REFRESH_TOKEN, null)
    }

    fun saveRefreshToken(token: String) {
        sharedPreferences.edit().putString(REFRESH_TOKEN, token).apply()
    }

    fun getTokenExpirationTime(): Long {
        return sharedPreferences.getLong(TOKEN_EXPIRATION, 0)
    }

    fun saveTokenExpirationTime(expirationTime: Long) {
        sharedPreferences.edit().putLong(TOKEN_EXPIRATION, expirationTime).apply()
    }

    fun clearTokens() {
        sharedPreferences.edit().remove(ID_TOKEN).apply()
        sharedPreferences.edit().remove(ACCESS_TOKEN).apply()
        sharedPreferences.edit().remove(REFRESH_TOKEN).apply()
        sharedPreferences.edit().remove(TOKEN_EXPIRATION).apply()
    }

    // Check if token needs refresh
    fun shouldRefreshToken(): Boolean {
        val currentTime = System.currentTimeMillis()
        val expirationTime = getTokenExpirationTime()
        return currentTime >= expirationTime
    }
}
