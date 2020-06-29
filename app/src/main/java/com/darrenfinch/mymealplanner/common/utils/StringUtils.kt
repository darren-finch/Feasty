package com.darrenfinch.mymealplanner.common.utils

import com.darrenfinch.mymealplanner.model.data.MacroType
import com.darrenfinch.mymealplanner.model.data.Food
import com.darrenfinch.mymealplanner.model.data.Meal
import com.darrenfinch.mymealplanner.model.data.MetricUnit

object StringUtils {
    private fun makeMacroNutrientString(
        calories: Int,
        protein: Int,
        carbohydrates: Int,
        fat: Int
    ): String {
        return "$calories calories | ${protein}P | ${carbohydrates}C | ${fat}F"
    }
//
//    fun makeMealFoodMacroNutrientsString(mealFood: MealFood): String {
//        return makeMacroNutrientString(mealFood.calories, mealFood.protein, mealFood.carbohydrates, mealFood.fat)
//    }

    fun makeFoodMacroNutrientsString(food: Food): String {
        return makeMacroNutrientString(food.macroNutrients.calories, food.macroNutrients.protein, food.macroNutrients.carbohydrates, food.macroNutrients.fat)
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

    fun makeFoodQuantityString(foodQuantity: Double, foodQuantityUnit: MetricUnit) : String {
        return "$foodQuantity $foodQuantityUnit"
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
        meal.foods.forEach { food -> totalCarbohydrates += food.macroNutrients.carbohydrates }
        meal.foods.forEach { food -> totalFat += food.macroNutrients.fat }

        return makeMacroNutrientString(totalCalories, totalProtein, totalCarbohydrates, totalFat)
    }
}