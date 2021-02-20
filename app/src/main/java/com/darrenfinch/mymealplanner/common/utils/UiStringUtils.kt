package com.darrenfinch.mymealplanner.common.utils

import com.darrenfinch.mymealplanner.screens.mealplan.MacroType

object UiStringUtils {
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