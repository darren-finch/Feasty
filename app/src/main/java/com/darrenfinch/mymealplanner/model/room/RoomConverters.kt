package com.darrenfinch.mymealplanner.model.room

import androidx.room.TypeConverter
import com.darrenfinch.mymealplanner.model.data.DatabaseMealFood
import com.darrenfinch.mymealplanner.model.data.MetricUnit
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
    fun convertMeasurementUnitToString(value: MetricUnit) = value.name

    @TypeConverter
    fun stringToMeasurementUnit(value: String): MetricUnit {
        return try {
            enumValueOf(value)
        } catch (e: Exception) {
            e.printStackTrace()
            MetricUnit.defaultUnit
        }
    }
}

