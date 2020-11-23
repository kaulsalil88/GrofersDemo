package com.example.grofersdemo.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize


//"1. open": "116.5400",
//            "2. high": "117.4500",
//            "3. low": "115.8900",
//            "4. close": "117.1800",
//            "5. volume": "3439648"

@Parcelize
data class StockPriceDetails(
    @Json(name = "1. open") val open: Float?,
    @Json(name="2. high") val high: Float?,
    @Json(name = "3. low") val low:Float?,
    @Json(name="4. close") val close:Float?,
    @Json(name="5.volume") val volume:Int?
): Parcelable