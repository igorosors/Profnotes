package com.example.profnotes.data.preferences

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private const val PREF_FILE_NAME = "regular_preferences_storage"

@Singleton
class PreferencesStorage @Inject constructor(
    @ApplicationContext val context: Context,
) {

    private val pref: SharedPreferences by lazy { createPreferences() }

    private fun createPreferences(): SharedPreferences {
        return context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
    }

    fun getString(key: String?, defValue: String? = null): String? = pref.getString(key, defValue)
    fun getInt(key: String?, defValue: Int = 0): Int = pref.getInt(key, defValue)
    fun getLong(key: String?, defValue: Long = 0L): Long = pref.getLong(key, defValue)
    fun getFloat(key: String?, defValue: Float = 0.0f): Float = pref.getFloat(key, defValue)
    fun getBoolean(key: String?, defValue: Boolean = false): Boolean = pref.getBoolean(key, defValue)

    fun edit(): SharedPreferences.Editor = pref.edit()


}