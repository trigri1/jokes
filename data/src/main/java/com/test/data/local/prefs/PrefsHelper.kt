package com.test.data.local.prefs

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefsHelper @Inject constructor(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val PREFS_USE_OFFLINE_MODE = "use_offline_mode"
        const val PREFS_CHARACTER_FIRST_NAME = "character_first_name"
        const val PREFS_CHARACTER_LAST_NAME = "character_last_name"
    }

    var offlineModeEnabled: Boolean
        get() = getBoolean(PREFS_USE_OFFLINE_MODE, false)
        set(value) = setBoolean(PREFS_USE_OFFLINE_MODE, value)

    var characterFirstName: String?
        get() = getString(PREFS_CHARACTER_FIRST_NAME)
        set(value) = setString(PREFS_CHARACTER_FIRST_NAME, value)

    var characterLastName: String?
        get() = getString(PREFS_CHARACTER_LAST_NAME)
        set(value) = setString(PREFS_CHARACTER_LAST_NAME, value)


    private fun setBoolean(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    private fun getBoolean(key: String, defaultValue: Boolean = false): Boolean =
        sharedPreferences.getBoolean(key, defaultValue)

    private fun setString(key: String, value: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    private fun getString(key: String, defaultValue: String? = null): String? =
        sharedPreferences.getString(key, defaultValue)
}