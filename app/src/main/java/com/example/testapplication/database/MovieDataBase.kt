package com.example.testapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.testapplication.Model.Result

@Database(entities = [Result::class], version =  2)
@TypeConverters(MovieTypeConvertor::class)

abstract class MovieDataBase: RoomDatabase() {
 abstract fun movieDao():MoviesDao
 companion object{
     @Volatile
     var instance: MovieDataBase?=null
     @Synchronized
      fun getInstance(context: Context): MovieDataBase {
         if(instance ==null) {
            instance = Room.databaseBuilder(
                context,
                MovieDataBase::class.java,
                "movie.db"
            ).fallbackToDestructiveMigration().build()
         }
         return instance as MovieDataBase
     }

 }
}