// Author: Jared Scott
package com.nscc

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat // Import SwitchCompat for compatibility
import android.widget.Button
import android.widget.ImageButton

class SettingsActivity : AppCompatActivity() {

    // Declare SwitchCompat to match the layout file and avoid casting issues
    private lateinit var darkModeSwitch: SwitchCompat
    private lateinit var clearHistoryButton: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Handle back button click
        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            // Navigate back to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()  // Close SettingsActivity

            // Initialize SwitchCompat and Button from layout
            darkModeSwitch = findViewById(R.id.darkModeSwitch)
            clearHistoryButton = findViewById(R.id.clearHistoryButton)

            // Set up SharedPreferences for saving settings
            sharedPreferences = getSharedPreferences("calculator_prefs", MODE_PRIVATE)

            // Initialize switch state based on saved preference
            darkModeSwitch.isChecked = sharedPreferences.getBoolean("dark_mode", false)

            // Listener for toggling dark mode
            darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // Enable dark mode and save preference
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    sharedPreferences.edit().putBoolean("dark_mode", true).apply()
                } else {
                    // Disable dark mode and save preference
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    sharedPreferences.edit().putBoolean("dark_mode", false).apply()
                }
            }

            // Listener for clearing history
            clearHistoryButton.setOnClickListener {
                clearHistory() // Clear calculator history from SharedPreferences
            }
        }

        // Method to clear calculator history from SharedPreferences
        fun clearHistory() {
            sharedPreferences.edit().remove("calculator_history").apply()
        }
    }
    // Would add More when pushing this project live
    private fun clearHistory() {
        TODO("Not yet implemented")
    }
}