package com.example.grofersdemo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import com.example.grofersdemo.databinding.ActivitySettingsBinding
import com.google.android.material.textfield.TextInputEditText

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = DataBindingUtil.setContentView<ActivitySettingsBinding>(
            this,
            R.layout.activity_settings
        )
        title = getString(R.string.settings_activity)
        binding.btFetchStockDetails.setOnClickListener {
            submitSymbol()
        }
        binding.tietStockSymbol.onSubmit { submitSymbol() }
    }

    fun TextInputEditText.onSubmit(func: () -> Unit) {
        setOnEditorActionListener { _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_DONE) {
                func()
            }

            true

        }
    }

    private fun submitSymbol(){
        val stockSymbol = binding.tietStockSymbol.text.toString()
        setResult(Activity.RESULT_OK, Intent().putExtra(KEY_STOCK_SYMBOL,stockSymbol))
        finish()
    }
    companion object {
        val KEY_STOCK_SYMBOL = "STOCK_SYMBOL"
    }
}