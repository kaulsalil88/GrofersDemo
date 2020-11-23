package com.example.grofersdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.grofersdemo.viewmodels.StockPriceViewModel

class StockDetailsActivity : AppCompatActivity() {

    lateinit var stockPriceViewModel: StockPriceViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //checkWithStrictMode()
        setContentView(R.layout.activity_stock_details)
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
                launchSettings()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun launchSettings() {

    }
}