package com.example.moviesapp.service

import com.example.moviesapp.model.Details
import com.example.moviesapp.model.MoviesModel
import com.example.moviesapp.model.Similar
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoviesAPIService {

    private val api = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MovieAPI::class.java)

    fun getPopularMoviesFromApi(page:Int =1) : Call<MoviesModel> {
        return api.getPopularMovies(page = page)
    }

    fun getDetailFromApi(movieID:Int) : Call<Details> {
        return api.getDetail(movieID)
    }

    fun getSimilarMoviesFromApi(movieID:Int) : Call<Similar>{
        return  api.getSimilarMovies(movieID)
    }
}