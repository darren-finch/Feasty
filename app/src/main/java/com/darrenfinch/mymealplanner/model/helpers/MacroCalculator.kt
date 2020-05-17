package com.darrenfinch.mymealplanner.model.helpers

import com.darrenfinch.mymealplanner.model.room.Food

object MacroCalculator
{
    fun calculateTotalCalories(foods: List<Food>) : Int
    {
        var totalCalories = 0
        foods.forEach { food -> totalCalories += food.calories }
        return totalCalories
    }
    fun calculateTotalProtein(foods: List<Food>) : Int
    {
        var totalProtein = 0
        foods.forEach { food -> totalProtein += food.protein }
        return totalProtein
    }
    fun calculateTotalCarbohydrates(foods: List<Food>) : Int
    {
        var totalCarbohydrates = 0
        foods.forEach { food -> totalCarbohydrates += food.carbohydrates }
        return totalCarbohydrates
    }
    fun calculateTotalFat(foods: List<Food>) : Int
    {
        var totalFat = 0
        foods.forEach { food -> totalFat += food.fat }
        return totalFat
    }
}