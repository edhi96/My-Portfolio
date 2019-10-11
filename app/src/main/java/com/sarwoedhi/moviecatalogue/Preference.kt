package com.sarwoedhi.moviecatalogue

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class Preference(val context: Context) {

    private val keyDaily = "DAILY_REMINDER"
    private val keyRelease = "RELEASE_REMINDER"
    private val appContext = context.applicationContext

    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun setDailyReminder(daily: Boolean) {
        preferences.edit().putBoolean(
            keyDaily,
            daily
        ).apply()
    }

    fun isDailyReminderEnabled(): Boolean {
        return preferences.getBoolean(keyDaily, true)
    }

    fun setReleaseReminder(release: Boolean) {
        preferences.edit().putBoolean(
            keyRelease,
            release
        ).apply()
    }

    fun isReleaseReminderEnabled(): Boolean {
        return preferences.getBoolean(keyRelease, true)
    }


}