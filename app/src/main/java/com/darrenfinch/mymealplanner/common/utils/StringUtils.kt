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
        return if (lineBreak) "$totalAmount/$requiredAmount\n${getMacroSuffix(macroType)}" else "$totalAmount/$requiredAmount ${getMacroSuffix(
            macroType
        )}"
    }

    fun getMacroSuffix(macroType: MacroType): String {
        return when (macroType) {
            MacroType.CALORIE -> "Calories"
            MacroType.PROTEIN -> "Proteins"
            MacroType.FAT -> "Fats"
            MacroType.CARBOHYDRATE -> "Carbs"
        }
    }

    fun calculateMealMacroNutrients(meal: Meal): String {
        var totalCalories = 0
        var totalProtein = 0
        var totalCarbohydrates = 0
        var totalFat = 0

        meal.foods.forEach { food -> totalCalories += food.macroNutrients.calories }
        meal.foods.forEach { food -> totalProtein += food.macroNutrients.protein }
        meal.foods.forEach { food -> totalCarbohydrates += food.macroNutrients.carbs }
        meal.foods.forEach { food -> totalFat += food.macroNutrients.fat }

        return MacroNutrients(totalCalories, totalCarbohydrates, totalProtein, totalFat).toString()
    }
}