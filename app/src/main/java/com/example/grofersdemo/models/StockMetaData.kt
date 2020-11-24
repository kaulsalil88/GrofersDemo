package com.example.grofersdemo.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize


//"1. Information": "Daily Prices (open, high, low, close) and Volumes",
//        "2. Symbol": "IBM",
//        "3. Last Refreshed": "2020-11-20",
//        "4. Output Size": "Compact",
//        "5. Time Zone": "US/Eastern"
@Parcelize
data class StockMetaData(
    @Json(name = "1. Information") val information: String?,
    @Json(name = "2. Symbol") val symbol: String,
    @Json(name = "3. Last Refreshed") val lastRefreshed: String?,
    @Json(name = "6. Time Zone") val timeZone: String?
) : Parcelable {
}