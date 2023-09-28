package com.example.testapplication.database

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MovieTypeConvertor {
     @TypeConverter
     fun anyToString(any:Any):String{
         return any as String
     }
    @TypeConverter
    fun stringToAny(any:Any):Any{
        return any
    }
    @TypeConverter
    fun stringToIntegerList(value: String?): List<Int> {
        return value?.split(",")?.map { it.toInt() } ?: emptyList()
    }

    @TypeConverter
    fun integerListToString(list: List<Int>?): String {
        return list?.joinToString(",") ?: ""
    }
}