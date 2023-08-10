package com.example.profnotes.data.repository

import com.example.profnotes.data.preferences.PreferencesStorage
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val preferencesStorage: PreferencesStorage,
) : TokenRepository {

    companion object {
        private const val KEY_TOKEN = "profile_token"
        private const val KEY_LOGIN = "profile_login"
        private const val KEY_PASSWORD = "profile_password"
    }

    override var token
        get() = preferencesStorage.getString(KEY_TOKEN, null)
        set(token) {
            preferencesStorage.edit().putString(KEY_TOKEN, token).commit()
        }

    override var login: String?
        get() = preferencesStorage.getString(KEY_LOGIN, null)
        set(login) {
            preferencesStorage.edit().putString(KEY_LOGIN, login).commit()
        }

    override var password: String?
        get() = preferencesStorage.getString(KEY_PASSWORD, null)
        set(password) {
            preferencesStorage.edit().putString(KEY_PASSWORD, password).commit()
        }
}