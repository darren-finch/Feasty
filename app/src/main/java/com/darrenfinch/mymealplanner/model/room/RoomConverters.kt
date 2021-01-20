package com.darrenfinch.mymealplanner.model.room

import androidx.room.TypeConverter
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.physicalquantities.stringsToUnits
import com.darrenfinch.mymealplanner.foods.models.MacroNutrients
import com.darrenfinch.mymealplanner.model.room.entities.DatabaseMealFood
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
    fun convertPhysicalQuantityToString(value: PhysicalQuantity) = "${value.quantity} ${value.unit.getUnitAsString(plural = false, abbreviated = false)}"

    @TypeConverter
    fun stringToPhysicalQuantity(value: String): PhysicalQuantity {
        return try {
            val strings = value.split(" ")
            val quantity = strings[0].toDouble()
            val unit = stringsToUnits[strings[1]]!!
            PhysicalQuantity(quantity, unit)
        } catch (e: Exception) {
            PhysicalQuantity.defaultPhysicalQuantity
        }
    }
}

