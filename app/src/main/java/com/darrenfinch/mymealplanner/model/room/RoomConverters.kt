package com.darrenfinch.mymealplanner.model.room

import androidx.room.TypeConverter
import com.darrenfinch.mymealplanner.model.data.DatabaseMealFood
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RoomConverters {
    @TypeConverter
    fun convertListOfMealFoodsToString(value: List<DatabaseMealFood>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun convertStringToListOfMealFoods(value: String): List<DatabaseMealFood> {
        val databaseMealFoodListType = object : TypeToken<List<DatabaseMealFood>>() {}.type
        return Gson().fromJson(value, databaseMealFoodListType)
    }

    @TypeConverter
    fun convertMeasurementUnitToString(value: MeasurementUnit) = value.name

    @TypeConverter
    fun stringToMeasurementUnit(value: String) = enumValueOf<MeasurementUnit>(value)
}