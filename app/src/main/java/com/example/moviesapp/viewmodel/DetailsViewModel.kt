package com.example.moviesapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesapp.model.Details
import com.example.moviesapp.model.Similar
import com.example.moviesapp.service.MoviesAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
//yusuf
class DetailsViewModel: ViewModel() {

    private val apiService = MoviesAPIService()

    var detailLiveData = MutableLiveData<Details>()
    var detailMoviesLoading = MutableLiveData<Boolean>()
    var detailMoviesError = MutableLiveData<Boolean>()

    var similarLiveData = MutableLiveData<Similar>()

    fun getData(movieID:Int){

        detailMoviesLoading.value = true

        apiService.getDetailFromApi(movieID)
            .enqueue(object : Callback<Details> {
                override fun onResponse(
                    call: Call<Details>,
                    response: Response<Details>
                ) {
                    if(response.isSuccessful){
                        detailMoviesLoading.value = false
                        detailLiveData.value = response.body()
                    }
                    else{
                        detailMoviesLoading.value = false
                        detailMoviesError.value = true
                    }
                }
                override fun onFailure(call: Call<Details>, t: Throwable) {
                    detailMoviesError.value = true
                    detailMoviesLoading.value = false
                }
            })
    }

    fun getSimilar(movieID:Int){

        apiService.getSimilarMoviesFromApi(movieID)
            .enqueue(object : Callback<Similar> {
                override fun onResponse(
                    call: Call<Similar>,
                    response: Response<Similar>
                ) {
                    if(response.isSuccessful){
                        similarLiveData.value = response.body()
                    }
                    else{

                    }
                }
                override fun onFailure(call: Call<Similar>, t: Throwable) {
                }
            })
    }


}