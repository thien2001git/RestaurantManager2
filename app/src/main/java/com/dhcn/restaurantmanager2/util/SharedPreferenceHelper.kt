package com.dhcn.restaurantmanager2.util

import android.app.Activity
import android.content.Context

object SharedPreferenceHelper {
    fun setBoolean(activity: Activity, key: String, value: Boolean) {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putBoolean(key, value)
            apply()
        }
    }

    fun getBoolean(activity: Activity, key: String, value: Boolean): Boolean {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return false
        return sharedPref.getBoolean(key, value)
    }
}