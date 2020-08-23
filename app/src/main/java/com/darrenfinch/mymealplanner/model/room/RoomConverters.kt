package com.darrenfinch.mymealplanner.model.room

import androidx.room.TypeConverter
import com.darrenfinch.mymealplanner.model.data.entitysubdata.MacroNutrients
import com.darrenfinch.mymealplanner.model.data.entitysubdata.MetricUnit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RoomConverters {
    @TypeConverter
    fun convertListOfMealFoodsToJsonString(value: List<DatabaseMealFood>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun convertJsonStringToListOfMealFoods(value: String): List<DatabaseMealFood> {
        val databaseMealFoodListType = object : TypeToken<List<DatabaseMealFood>>() {}.type
        return Gson().fromJson(value, databaseMealFoodListType)
    }

    @TypeConverter
    fun convertMacroNutrientsToJsonString(value: MacroNutrients): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun convertJsonStringToMacroNutrients(value: String): MacroNutrients {
        return Gson().fromJson(value, MacroNutrients::class.java)
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

