package com.example.grofersdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        title = getString(R.string.settings_activity)
    }

    companion object {
        val KEY_STOCK_SYMBOL = "STOCK_SYMBOL"
    }
}