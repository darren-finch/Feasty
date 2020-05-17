package com.darrenfinch.mymealplanner.model.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

//class Converters
//{
//    @TypeConverter
//    fun listOfFoodToString(foods: List<Food>) : String
//    {
//        val myGson = Gson()
//        return myGson.toJson(foods)
//    }
//
//    @TypeConverter
//    fun stringToListOfFood(json: String) : List<Food>
//    {
//        val myGson = Gson()
//        val listOfFoodType: Type = object : TypeToken<List<Food?>?>(){}.type
//        return myGson.fromJson(json, listOfFoodType)
//    }
//}