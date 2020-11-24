package com.example.grofersdemo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.grofersdemo.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySettingsBinding>(
            this,
            R.layout.activity_settings
        )
        title = getString(R.string.settings_activity)
        binding.btFetchStockDetails.setOnClickListener {
            val stockSymbol = binding.tietStockSymbol.text.toString()
            setResult(Activity.RESULT_OK, Intent().putExtra(KEY_STOCK_SYMBOL,stockSymbol))
            finish()
        }
    }

    companion object {
        val KEY_STOCK_SYMBOL = "STOCK_SYMBOL"
    }
}