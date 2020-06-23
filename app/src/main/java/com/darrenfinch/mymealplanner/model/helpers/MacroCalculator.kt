package com.darrenfinch.mymealplanner.model.helpers

import com.darrenfinch.mymealplanner.model.data.Food

object MacroCalculator
{
    fun calculateTotalCalories(foods: List<Food>) : Int
    {
        var totalCalories = 0
        foods.forEach { food -> totalCalories += food.macroNutrients.calories }
        return totalCalories
    }
    fun calculateTotalProtein(foods: List<Food>) : Int
    {
        var totalProtein = 0
        foods.forEach { food -> totalProtein += food.macroNutrients.protein }
        return totalProtein
    }
    fun calculateTotalCarbohydrates(foods: List<Food>) : Int
    {
        var totalCarbohydrates = 0
        foods.forEach { food -> totalCarbohydrates += food.macroNutrients.carbohydrates }
        return totalCarbohydrates
    }
    fun calculateTotalFat(foods: List<Food>) : Int
    {
        var totalFat = 0
        foods.forEach { food -> totalFat += food.macroNutrients.fat }
        return totalFat
    }
}