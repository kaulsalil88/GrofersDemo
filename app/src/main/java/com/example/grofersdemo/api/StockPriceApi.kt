package com.example.grofersdemo.api

import com.example.grofersdemo.models.StockApiDetails
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

//val interceptor =
private const val BASE_URL = "https://www.alphavantage.co/"
private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory()).baseUrl(BASE_URL).build()

//interface GitHubApi {
//
//    //Add the various actions here .
//    @GET("repositories?q=android+language:kotlin+language:java&sort=stars&order=desc")
//    fun getPopularAndroidRepoAsync():Deferred<List<RepositoryDataClass>>
//}
//
//object GitHubApiService {
//    val retrofitService : GitHubApi by lazy { retrofit.create(GitHubApi::class.java) }
//}

//API KEY 0D2ZREIKANHDZGFR
interface StockApi{
    @GET("query?function=TIME_SERIES_DAILY&apikey=0D2ZREIKANHDZGFR")
    fun getStockDetails(@Query("symbol")stock:String):Deferred<StockApiDetails>

}

object  StockApiService {
    val retrofitService:StockApi by lazy  { retrofit.create(StockApi::class.java)}
}