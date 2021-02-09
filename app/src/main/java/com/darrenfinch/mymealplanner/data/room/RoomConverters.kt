package com.darrenfinch.mymealplanner.data.room

import androidx.room.TypeConverter
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.physicalquantities.stringsToUnits
import com.darrenfinch.mymealplanner.data.room.models.foods.DbMacroNutrients
import com.google.gson.Gson

class RoomConverters {
    @TypeConverter
    fun convertMacroNutrientsToJsonString(value: DbMacroNutrients): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun convertJsonStringToMacroNutrients(value: String): DbMacroNutrients {
        return Gson().fromJson(value, DbMacroNutrients::class.java)
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

