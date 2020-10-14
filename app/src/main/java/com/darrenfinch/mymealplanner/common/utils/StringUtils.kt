package com.darrenfinch.mymealplanner.common.utils

import com.darrenfinch.mymealplanner.model.data.entities.Food
import com.darrenfinch.mymealplanner.model.data.entities.Meal
import com.darrenfinch.mymealplanner.model.data.entities.MealFood
import com.darrenfinch.mymealplanner.model.data.entitysubdata.MacroType
import com.darrenfinch.mymealplanner.model.data.entitysubdata.MetricUnit

object StringUtils {
    private fun makeMacroNutrientString(
        calories: Int,
        protein: Int,
        carbohydrates: Int,
        fat: Int
    ): String {
        return "$calories calories | ${protein}P | ${carbohydrates}C | ${fat}F"
    }

    fun getMealTitleTemp(title: String) = if (title.isNotEmpty()) title else "(enter name)"

    fun makeMealFoodMacroNutrientsString(mealFood: MealFood): String {
        return makeMacroNutrientString(
            mealFood.macroNutrients.calories,
            mealFood.macroNutrients.protein,
            mealFood.macroNutrients.carbs,
            mealFood.macroNutrients.fat
        )
    }

    fun makeFoodMacroNutrientsString(food: Food): String {
        return makeMacroNutrientString(
            food.macroNutrients.calories,
            food.macroNutrients.protein,
            food.macroNutrients.carbs,
            food.macroNutrients.fat
        )
    }

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

        return makeMacroNutrientString(totalCalories, totalProtein, totalCarbohydrates, totalFat)
    }
}