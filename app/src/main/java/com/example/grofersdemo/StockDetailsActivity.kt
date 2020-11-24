package com.example.grofersdemo

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.icu.util.TimeUnit
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.util.TimeUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.grofersdemo.databinding.ActivityStockDetailsBinding
import com.example.grofersdemo.models.StockApiDetails
import com.example.grofersdemo.viewmodels.ApiStatus
import com.example.grofersdemo.viewmodels.StockPriceViewModel

class StockDetailsActivity : AppCompatActivity() {

    //60 * 60 * 1000
    private val delayAfterWhichToFetchStocksMs: Long = 1000

    val TAG = StockDetailsActivity::class.java.name
    lateinit var stockPriceViewModel: StockPriceViewModel
    private lateinit var binding: ActivityStockDetailsBinding

    //Stock Symbol returned from the  Settings Activity
    private var stockSymbol: String = "GOOG"

    //Receiver to fetch stock details after an hour
    val messageReceiver = object : BroadcastReceiver() {

        override fun onReceive(ctx: Context?, intent: Intent?) {
            Log.e(TAG, "BroadCast Received")
            stockPriceViewModel.getStockDetails(stockSymbol)

        }

    }


    //The API for starting activity for result
    private val openSettingActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            it.apply {
                if (it.resultCode == Activity.RESULT_OK) {

                    Log.d(
                        TAG,
                        "First One: ${it.data?.getStringExtra(SettingsActivity.KEY_STOCK_SYMBOL)}"
                    )


                    it.data?.getStringExtra(SettingsActivity.KEY_STOCK_SYMBOL)?.let { symbol ->
                        stockSymbol = symbol
                        stockPriceViewModel.getStockDetails(
                            stockSymbol
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

    //Function for observing the viewmodels live data
    fun setUpObservers() {
        stockPriceViewModel.status.observe(this, Observer {
            when (it) {
                ApiStatus.ERROR -> {
                    binding.pb.visibility = View.GONE
                }
                ApiStatus.LOADING -> {
                    binding.pb.visibility = View.VISIBLE
                }
                ApiStatus.DONE -> {
                    binding.pb.visibility = View.GONE
                }
            }

        })

        stockPriceViewModel.result.observe(this, Observer {
            setStockValues(it)
            //fetchStockDataAfterAnHour()
            //fetchStockUsingHandler()

        })

        LocalBroadcastManager.getInstance(this).registerReceiver(
            messageReceiver,
            IntentFilter(ACTION_FETCH_STOCKS_IN_HOUR)
        );
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver)
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

    //Not Being Used
    private fun fetchStockDataAfterAnHour() {

        val alarmManager =
            getSystemService(Context.ALARM_SERVICE) as? AlarmManager

        val broadCastIntent = Intent(this, StockDetailsActivity::class.java)

        broadCastIntent.action = ACTION_FETCH_STOCKS_IN_HOUR
        broadCastIntent.setClass(this, StockDetailsActivity::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            this,
            PENDING_INTENT_RESULT_CODE,
            broadCastIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager?.set(
            AlarmManager.ELAPSED_REALTIME,
            SystemClock.elapsedRealtime() + 2_000,

            pendingIntent
        )
    }

    private fun fetchStockUsingHandler() {
        Handler(Looper.getMainLooper()).postDelayed({
            stockPriceViewModel.getStockDetails(stockSymbol)
        }, delayAfterWhichToFetchStocksMs)

    }

    companion object {
        val ACTION_FETCH_STOCKS_IN_HOUR = "fetch-stock-in-hour"
        val PENDING_INTENT_RESULT_CODE = 1001
    }


}