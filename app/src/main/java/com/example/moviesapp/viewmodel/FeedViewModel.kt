package com.example.moviesapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesapp.model.MoviesModel
import com.example.moviesapp.service.MoviesAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedViewModel : ViewModel() {
    private val apiService = MoviesAPIService()

    var popularMoviesLiveData = MutableLiveData<MoviesModel>()
    var popularMoviesLoading = MutableLiveData<Boolean>()
    var popularMoviesError = MutableLiveData<Boolean>()
    private var pageNumber = MutableLiveData<Int>(1)

    fun refreshData(){
        getDataFromApi(pageNumber.value!!)
    }

    fun minusPageNumber(){

        if (pageNumber.value!! > 1){
            pageNumber.value = pageNumber.value?.minus(1)
        }

    }

    fun addPageNumber(){
        pageNumber.value = pageNumber.value?.plus(1)
    }

    private fun getDataFromApi(pageNumber:Int=1){
        popularMoviesLoading.value = true

        apiService.getPopularMoviesFromApi(pageNumber)
            .enqueue(object : Callback<MoviesModel> {
            override fun onResponse(
                call: Call<MoviesModel>,
                response: Response<MoviesModel>
            ) {
                if(response.isSuccessful){
                    popularMoviesLoading.value = false
                    popularMoviesLiveData.value = response.body()
                }
                else{
                    popularMoviesLoading.value = false
                    popularMoviesError.value = true
                }
            }

            override fun onFailure(call: Call<MoviesModel>, t: Throwable) {
                popularMoviesError.value = true
                popularMoviesLoading.value = false
            }
        })

    }
}