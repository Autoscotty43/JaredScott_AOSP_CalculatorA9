package com.nscc

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import android.widget.Switch
import android.widget.Button

class SettingsActivity : AppCompatActivity() {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var darkModeSwitch: Switch
    private lateinit var clearHistoryButton: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        darkModeSwitch = findViewById(R.id.darkModeSwitch)

        sharedPreferences = getSharedPreferences("calculator_prefs", MODE_PRIVATE)

        darkModeSwitch = findViewById(R.id.darkModeSwitch)
        clearHistoryButton = findViewById(R.id.clearHistoryButton)

        darkModeSwitch.isChecked = sharedPreferences.getBoolean("dark_mode", false)

        // Dark mode toggle listener
        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPreferences.edit().putBoolean("dark_mode", true).apply()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPreferences.edit().putBoolean("dark_mode", false).apply()
            }
        }

        // Clear history button listener
        clearHistoryButton.setOnClickListener {
            // Clear history logic
            clearHistory()
        }
    }

    private fun clearHistory() {
        sharedPreferences.edit().remove("calculator_history").apply()
    }
}