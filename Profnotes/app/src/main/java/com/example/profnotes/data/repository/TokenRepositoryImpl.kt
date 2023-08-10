package com.example.profnotes.data.repository

import android.content.SharedPreferences
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : TokenRepository {

    companion object {
        private const val KEY_TOKEN = "profile_token"
        private const val KEY_LOGIN = "profile_login"
        private const val KEY_PASSWORD = "profile_password"
    }

    override var token
        get() = sharedPreferences.getString(KEY_TOKEN, null)
        set(token) {
            sharedPreferences.edit().putString(KEY_TOKEN, token).commit()
        }

    override var login: String?
        get() = sharedPreferences.getString(KEY_LOGIN, null)
        set(login) {
            sharedPreferences.edit().putString(KEY_LOGIN, login).commit()
        }

    override var password: String?
        get() = sharedPreferences.getString(KEY_PASSWORD, null)
        set(password) {
            sharedPreferences.edit().putString(KEY_PASSWORD, password).commit()
        }
}