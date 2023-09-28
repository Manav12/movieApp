package com.example.testapplication.Api

import com.example.testapplication.Model.MovieTileData
import retrofit2.http.GET


interface MovieApi {
    @GET("movie/popular")
    fun getPopularMovies(): retrofit2.Call<MovieTileData>

}