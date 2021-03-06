package com.example.grofersdemo.models

import com.squareup.moshi.Json


data class StockApiDetails(
    @Json(name = "Meta Data") val stockMetaData: StockMetaData?,
    @Json(name = "Time Series (5min)") val timeToStockDetailsMap: MutableMap<String, StockPriceDetails>?
) {
}