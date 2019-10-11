package com.sarwoedhi.moviecatalogue.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sarwoedhi.moviecatalogue.Preference

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private var pref: Preference = Preference(application.applicationContext)

    fun setDailyReminder(daily: Boolean) {
        pref.setDailyReminder(daily)
    }

    fun isDailyReminderEnabled(): Boolean {
        return pref.isDailyReminderEnabled()
    }

    fun setReleaseReminder(release: Boolean) {
        pref.setReleaseReminder(release)
    }

    fun isReleaseReminderEnabled(): Boolean {
        return pref.isReleaseReminderEnabled()
    }

}