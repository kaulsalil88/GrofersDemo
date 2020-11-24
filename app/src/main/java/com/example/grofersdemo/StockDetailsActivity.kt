package com.example.grofersdemo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.grofersdemo.databinding.ActivityStockDetailsBinding
import com.example.grofersdemo.models.StockApiDetails
import com.example.grofersdemo.viewmodels.StockPriceViewModel

class StockDetailsActivity : AppCompatActivity() {

    val TAG = StockDetailsActivity::class.java.name
    lateinit var stockPriceViewModel: StockPriceViewModel
    private lateinit var binding: ActivityStockDetailsBinding


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

                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_stock_details)
        title = getString(R.string.stock_details)
        stockPriceViewModel = ViewModelProviders.of(this)[StockPriceViewModel::class.java]
        setUpObservers()
        stockPriceViewModel.getStockDetails()
    }

    fun setUpObservers() {
        stockPriceViewModel.status.observe(this, Observer {

        })

        stockPriceViewModel.result.observe(this, Observer {
            setStockValues(it)

        })
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


    private fun setStockValues(stockApiDetails: StockApiDetails) {
       val keys = stockApiDetails.timeToStockDetailsMap?.keys
       val stockPriceDetails = stockApiDetails.timeToStockDetailsMap?.get(keys?.first())

        binding.tvOpenPriceVal.text = stockPriceDetails?.open.toString()
        binding.tvIdHiVal.text = stockPriceDetails?.high.toString()
        binding.tvIdLoVal.text = stockPriceDetails?.low.toString()
        binding.tvOpenCurrentVal.text = stockPriceDetails?.close.toString()
        binding.tvSymVal.text = stockApiDetails.stockMetaData?.symbol
    }

    private fun fetchStockDataAfterAnHour() {

    }


}