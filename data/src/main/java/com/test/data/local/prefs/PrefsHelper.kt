package com.test.data.local.prefs

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefsHelper @Inject constructor(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val PREFS_USE_OFFLINE_MODE = "use_offline_mode"
    }

    var offlineModeEnabled: Boolean
        get() = getBoolean(PREFS_USE_OFFLINE_MODE, false)
        set(value) = setBooleanValue(PREFS_USE_OFFLINE_MODE, value)

    private fun setBooleanValue(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    private fun getBoolean(key: String, defaultValue: Boolean = false): Boolean =
        sharedPreferences.getBoolean(key, defaultValue)

}