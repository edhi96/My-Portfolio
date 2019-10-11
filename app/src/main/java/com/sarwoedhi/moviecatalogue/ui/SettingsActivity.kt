package com.sarwoedhi.moviecatalogue.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.sarwoedhi.moviecatalogue.AlarmReceiver
import com.sarwoedhi.moviecatalogue.R
import com.sarwoedhi.moviecatalogue.viewmodels.SettingsViewModel

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager.beginTransaction().replace(
            R.id.settings,
            SettingsFragment()
        )
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        private lateinit var settingsViewModel: SettingsViewModel

        private var alarmReceiver = AlarmReceiver()

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            activity?.let {
                settingsViewModel = ViewModelProviders.of(it).get(SettingsViewModel::class.java)
            }
            val dailyReminderSwitch = findPreference<SwitchPreferenceCompat>("daily_reminder")
            val releaseReminderSwitch = findPreference<SwitchPreferenceCompat>("release_reminder")
            val languagePreference = findPreference<Preference>("preference_language")
            dailyReminderSwitch?.isChecked = settingsViewModel.isDailyReminderEnabled() == false
            releaseReminderSwitch?.isChecked = settingsViewModel.isReleaseReminderEnabled() == false
            dailyReminderSwitch?.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { preference, _ ->
                    val dailySwitch = preference as SwitchPreferenceCompat
                    if (dailySwitch.isChecked) {
                        settingsViewModel.setDailyReminder(true)
                        activity?.let {
                            alarmReceiver.cancelAlarm(it)
                        }
                    } else {
                        settingsViewModel.setDailyReminder(false)
                        activity?.let {
                            alarmReceiver.setRepeatingAlarm(it)
                        }
                    }
                    true
                }

            releaseReminderSwitch?.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { preference, _ ->
                    val releaseSwitch = preference as SwitchPreferenceCompat
                    if (releaseSwitch.isChecked) {
                        settingsViewModel.setReleaseReminder(true)
                        activity?.let {
                            alarmReceiver.cancelMovieAlarm(it)
                        }
                    } else {
                        settingsViewModel.setReleaseReminder(false)
                        activity?.let {
                            alarmReceiver.setMovieAlarm(it)
                        }
                    }
                    true
                }

            languagePreference?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
                true
            }
        }

    }
}