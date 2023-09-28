package com.example.testapplication.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testapplication.Model.Result

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResults(results: List<Result>)

    @Query("SELECT * FROM movie_results")
    suspend fun getAllResults(): List<Result>}