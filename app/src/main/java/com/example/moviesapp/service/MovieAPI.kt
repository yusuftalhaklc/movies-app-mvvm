package com.example.moviesapp.service

import com.example.moviesapp.model.Details
import com.example.moviesapp.model.MoviesModel
import com.example.moviesapp.model.Similar
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {

    @GET("movie/popular?api_key=4a031d87b15270645ced4aa98b8c104e&language=en-US")
    fun getPopularMovies(@Query("page")page:Int = 1): Call<MoviesModel>

    @GET("movie/{id}?api_key=4a031d87b15270645ced4aa98b8c104e&language=en-US")
    fun getDetail(@Path("id") movieID:Int): Call<Details>


    @GET("movie/{id}/similar?api_key=4a031d87b15270645ced4aa98b8c104e&language=en-US&page=1")
    fun getSimilarMovies(@Path("id") movieID:Int): Call<Similar>
}