package com.darrenfinch.mymealplanner.data

data class MealModel(var title: String, var foods: List<FoodModel>)
{
    fun calculateTotalCalories() : Int
    {
        var totalCalories = 0
        foods.forEach { food -> totalCalories += food.calories }
        return totalCalories
    }
    fun calculateTotalProtein() : Int
    {
        var totalProtein = 0
        foods.forEach { food -> totalProtein += food.protein }
        return totalProtein
    }
    fun calculateTotalCarbohydrates() : Int
    {
        var totalCarbohydrates = 0
        foods.forEach { food -> totalCarbohydrates += food.carbohydrates }
        return totalCarbohydrates
    }
    fun calculateTotalFat() : Int
    {
        var totalFat = 0
        foods.forEach { food -> totalFat += food.fat }
        return totalFat
    }
}