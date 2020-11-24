package com.example.grofersdemo.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.grofersdemo.api.StockApiService
import com.example.grofersdemo.models.StockApiDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

//enum class GitApiStatus { LOADING, ERROR, DONE }

enum class ApiStatus { LOADING, ERROR, DONE }
class StockPriceViewModel : ViewModel() {

    private val TAG = StockPriceViewModel::class.java.name


    private val _stockApiResponse = MutableLiveData<StockApiDetails>()

    val result: LiveData<StockApiDetails> get() = _stockApiResponse

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> get() = _status
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    //ToDo:Correct the coroutine behaviour
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun getStockDetails(stockSymbol: String = "GOOG") {
        coroutineScope.launch {
            val deferred = StockApiService.retrofitService.getStockDetails(stockSymbol)
            try {
                _status.value = ApiStatus.LOADING
                _stockApiResponse.value = deferred.await()
                Log.d(TAG, "Stock API Response: $_stockApiResponse")
                _status.value = ApiStatus.DONE

            } catch (ex: Exception) {
                Log.e(TAG, ex.localizedMessage)
                _status.value = ApiStatus.ERROR

            }
        }
    }


    //In case we need to do any view model related clean up data
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()

    }

}