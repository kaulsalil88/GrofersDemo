package com.example.grofersdemo

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class SettingsActivityContract : ActivityResultContract<Unit,String?>() {

    override fun createIntent(context: Context, input: Unit?): Intent {
        return Intent(context,SettingsActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        val data = intent?.getStringExtra(SettingsActivity.KEY_STOCK_SYMBOL)
        return if (resultCode == Activity.RESULT_OK && data != null) data
        else null

    }
}