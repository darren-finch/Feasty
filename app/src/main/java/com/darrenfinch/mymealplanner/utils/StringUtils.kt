package com.darrenfinch.mymealplanner.utils

import com.darrenfinch.mymealplanner.model.room.Food
import com.darrenfinch.mymealplanner.model.helpers.MacroType
import com.darrenfinch.mymealplanner.model.room.Meal

object StringUtils
{
    private fun makeMacroNutrientString(calories: Int, protein: Int, carbohydrates: Int, fat: Int) : String
    {
        return "$calories calories | ${protein}P | ${carbohydrates}C | ${fat}F"
    }
    fun makeFoodMacroNutrientsString(food: Food) : String
    {
        return makeMacroNutrientString(food.calories, food.protein, food.carbohydrates, food.fat)
    }
    fun makeTotalVsRequiredNutrientString(totalAmount: Int, requiredAmount: Int, macroType: MacroType, lineBreak: Boolean = true) : String
    {
        return if (lineBreak) "$totalAmount/$requiredAmount\n${getMacroSuffix(macroType)}" else "$totalAmount/$requiredAmount ${getMacroSuffix(macroType)}"
    }
    fun getMacroSuffix(macroType: MacroType) : String
    {
        return when (macroType)
        {
            MacroType.CALORIE -> "Calories"
            MacroType.PROTEIN -> "Proteins"
            MacroType.FAT -> "Fats"
            MacroType.CARBOHYDRATE -> "Carbs"
        }
    }
    fun calculateMealMacroNutrients(meal: Meal) : String
    {
        var totalCalories = 0
        var totalProtein = 0
        var totalCarbohydrates = 0
        var totalFat = 0

        meal.foods.forEach { food -> totalCalories += food.calories }
        meal.foods.forEach { food -> totalProtein += food.protein }
        meal.foods.forEach { food -> totalCarbohydrates += food.carbohydrates }
        meal.foods.forEach { food -> totalFat += food.fat }

        return makeMacroNutrientString(totalCalories, totalProtein, totalCarbohydrates, totalFat)
    }
}