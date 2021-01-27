package com.darrenfinch.mymealplanner.common.helpers

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.FragmentActivity

class SharedPreferencesHelper(private val activity: FragmentActivity) {
    companion object {
        const val DEFAULT_INT = 0
    }

    private val preferences: SharedPreferences
        get() = activity.getPreferences(Context.MODE_PRIVATE)

    fun putInt(key: String, value: Int) {
        preferences.edit().putInt(key, value).apply()
    }

    fun getInt(key: String): Int {
        return preferences.getInt(key, DEFAULT_INT)
    }
}