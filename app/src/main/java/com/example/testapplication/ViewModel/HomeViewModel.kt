package com.example.testapplication.ViewModel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testapplication.Api.RetrofitInstance
import com.example.testapplication.Model.MovieTileData
import com.example.testapplication.Model.Result
import com.example.testapplication.database.MovieDataBase
import com.example.testapplication.database.MoviesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(application: Application): AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    var movieList = MutableLiveData<MovieTileData>()

    private val appContext = application.applicationContext
    private val movieDatabase = MovieDataBase.getInstance(application)
    var loadingState = MutableLiveData<Int>()
    fun insertResults(results: List<Result>) {
        viewModelScope.launch {
            movieDatabase.movieDao().insertResults(results)
        }
    }

    suspend fun getAllResults(): List<Result> {
        return movieDatabase.movieDao().getAllResults()
    }
    init {
        getMovieData()
    }

    private fun getMovieData() {
        RetrofitInstance.movieApi.getPopularMovies().enqueue(object : Callback<MovieTileData?> {
            override fun onResponse(
                call: Call<MovieTileData?>,
                response: Response<MovieTileData?>
            ) {

                if(response.isSuccessful){
                    movieList.value = response.body()
                    val results = response.body()!!.results
                    val resultEntities = results.map { result ->
                        Result(
                            id = result.id,
                            adult = result.adult,
                            backdrop_path = result.backdrop_path,
                            genre_ids = result.genre_ids,
                            original_language = result.original_language,
                            original_title = result.original_title,
                            overview = result.overview,
                            popularity = result.popularity,
                            poster_path = result.poster_path,
                            release_date = result.release_date,
                            title = result.title,
                            video = result.video,
                            vote_average = result.vote_average,
                            vote_count = result.vote_count
                        )
                    }
                    insertResults(resultEntities)
                    loadingState.value = 2
                } else{
                    Toast.makeText(appContext, "response.code().toString()", Toast.LENGTH_SHORT).show()
                    loadingState.value = 2
                }
            }
            override fun onFailure(call: Call<MovieTileData?>, t: Throwable) {

                Toast.makeText(appContext, "Internet Not Available", Toast.LENGTH_SHORT).show()
                Log.e("TAG", t.message.toString())
                loadingState.value = 1

            }
        })
    }


}