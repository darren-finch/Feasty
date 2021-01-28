package com.darrenfinch.mymealplanner.model.room

import androidx.room.TypeConverter
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.physicalquantities.stringsToUnits
import com.darrenfinch.mymealplanner.foods.models.domain.MacroNutrients
import com.darrenfinch.mymealplanner.foods.models.presentation.UiMacroNutrients
import com.darrenfinch.mymealplanner.model.room.models.foods.DatabaseMacroNutrients
import com.darrenfinch.mymealplanner.model.room.models.meals.DatabaseMealFood
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RoomConverters {
    @TypeConverter
    fun convertMacroNutrientsToJsonString(value: DatabaseMacroNutrients): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun convertJsonStringToMacroNutrients(value: String): DatabaseMacroNutrients {
        return Gson().fromJson(value, DatabaseMacroNutrients::class.java)
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

