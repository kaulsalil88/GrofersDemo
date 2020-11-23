package com.example.grofersdemo.models

import com.squareup.moshi.Json


data class StockDetails(
    @Json(name = "Meta Data") val stockMetaData: StockMetaData?,
    @Json(name = "Time Series (Daily)") val timeToStockDetailsMap: MutableMap<String, StockPriceDetails>?
) {
}