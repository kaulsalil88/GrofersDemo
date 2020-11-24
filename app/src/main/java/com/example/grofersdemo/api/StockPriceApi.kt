package com.example.grofersdemo.api

import com.example.grofersdemo.models.StockApiDetails
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.logging.Level

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()


private const val BASE_URL = "https://www.alphavantage.co/"

//addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory()).baseUrl(BASE_URL)
    .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)).build()).build()



interface StockApi {
    @GET("query?function=TIME_SERIES_INTRADAY&interval=5min&apikey=0D2ZREIKANHDZGFR")
    fun getStockDetailsAsync(@Query("symbol") stock: String): Deferred<StockApiDetails>

}


object StockApiService {


    val retrofitService: StockApi by lazy { retrofit.create(StockApi::class.java) }
}