package com.darrenfinch.mymealplanner.common.utils

import com.darrenfinch.mymealplanner.model.data.entities.Food
import com.darrenfinch.mymealplanner.model.data.entities.Meal
import com.darrenfinch.mymealplanner.model.data.entities.MealFood
import com.darrenfinch.mymealplanner.model.data.entitysubdata.MacroNutrients
import com.darrenfinch.mymealplanner.model.data.entitysubdata.MacroType
import com.darrenfinch.mymealplanner.model.data.entitysubdata.MetricUnit

object StringUtils {
    fun getMealTitleTemp(title: String) = if (title.isNotEmpty()) title else "(enter name)"

    fun makeTotalVsRequiredNutrientString(
        totalAmount: Int,
        requiredAmount: Int,
        macroType: MacroType,
        lineBreak: Boolean = true
    ): String {
        return "$totalAmount/$requiredAmount${if (lineBreak) "\n" else " "}${getMacroSuffix(macroType)}"
    }

    private fun getMacroSuffix(macroType: MacroType): String {
        return when (macroType) {
            MacroType.CALORIE -> "Calories"
            MacroType.PROTEIN -> "Proteins"
            MacroType.FAT -> "Fats"
            MacroType.CARBOHYDRATE -> "Carbs"
        }
    }
}