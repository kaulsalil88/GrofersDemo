package com.example.grofersdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
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
}