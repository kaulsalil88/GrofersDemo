package com.example.grofersdemo

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.grofersdemo.viewmodels.StockPriceViewModel

class StockDetailsActivity : AppCompatActivity() {

    val TAG = StockDetailsActivity::class.java.name
    lateinit var stockPriceViewModel: StockPriceViewModel


    private val openSettingActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            it.apply {
                if (it.resultCode == Activity.RESULT_OK) {

                    Log.d(
                        TAG,
                        "First One: ${it.data?.getStringExtra(SettingsActivity.KEY_STOCK_SYMBOL)}"
                    )
                    it.data?.getStringExtra(SettingsActivity.KEY_STOCK_SYMBOL)?.let { symbol ->
                        stockPriceViewModel.getStockDetails(
                            symbol
                        )
                    }
                    //Make Fresh API Call with New Symbol
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //checkWithStrictMode()
        setContentView(R.layout.activity_stock_details)
        title = getString(R.string.stock_details)
        stockPriceViewModel = ViewModelProviders.of(this)[StockPriceViewModel::class.java]
        setUpObservers()
        stockPriceViewModel.getStockDetails()
    }

    fun setUpObservers() {
        stockPriceViewModel.status.observe(this, Observer {

        })

        stockPriceViewModel.result.observe(this, Observer {

        })
    }

    fun checkWithStrictMode() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder().detectAll().penaltyDeath().build()
        )

        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().penaltyDeath().build())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_stock_details_screen, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.settings_screen -> {
                launchSettingsScreen()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun launchSettingsScreen() {
        val startSettingsActivityIntent =
            Intent(this, SettingsActivity::class.java).setAction("StockDetails")
        openSettingActivityLauncher.launch(startSettingsActivityIntent)

    }

    private fun fetchStockDataAfterAnHour(){

    }


}