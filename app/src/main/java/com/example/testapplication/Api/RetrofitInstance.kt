package com.example.testapplication.Api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://api.themoviedb.org/3/"

const val BASE_BACKDROP_IMAGE_URL = "https://image.tmdb.org/t/p/w1280"
const val BASE_POSTER_IMAGE_URL = "https://image.tmdb.org/t/p/w342"
val headerInterceptor = Interceptor { chain ->
    val originalRequest = chain.request()
    val modifiedRequest: Request = originalRequest.newBuilder()
        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlOTEwYWEwNjNlZDZiMjY2MzU3NWE5YzYwYjAwNjJiZiIsInN1YiI6IjY1MGQ4NzUzOTNkYjkyMDExYmI4NTM0NiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.pBxJm-flnamVIbNUn9hYb1NCym04AzR-56pfMcz_KDk") // Add your authorization header here
        .build()

    chain.proceed(modifiedRequest)
}



private val okHttpClient = OkHttpClient.Builder().addInterceptor(headerInterceptor)
    .connectTimeout(300, TimeUnit.SECONDS)
    .readTimeout(300, TimeUnit.SECONDS)
    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    .build()

private val retrofit = Retrofit.Builder().client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

object RetrofitInstance{
    val movieApi: MovieApi by lazy {
        retrofit.create(MovieApi::class.java)
    }

}