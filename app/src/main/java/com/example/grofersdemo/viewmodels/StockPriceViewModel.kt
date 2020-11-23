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


    //
    //    fun getRepositories() {
    //        coroutineScope.launch {
    //            val deffered = GitHubApiService.retrofitService.getPopularAndroidRepoAsync()
    //            try {
    //                _status.value = GitApiStatus.LOADING
    //                _result.value = deffered.await()
    //                Log.e("MainViewModel", "Success" + result.value?.size)
    //                _status.value = GitApiStatus.DONE
    //
    //            } catch (ex: Exception) {
    //                _status.value = GitApiStatus.ERROR
    //                Log.e("MainViewModel", "Failure" + ex.localizedMessage)
    //            }
    //
    //        }
    //    }

    private val _stockApiResponse = MutableLiveData<StockApiDetails>()

    val result: LiveData<StockApiDetails> get() = _stockApiResponse

    private val _status = MutableLiveData<ApiStatus>()
     val status:LiveData<ApiStatus> get() = _status
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun getStockDetails() {
        coroutineScope.launch {
            val deferred = StockApiService.retrofitService.getStockDetails("IBM")
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
    }

}