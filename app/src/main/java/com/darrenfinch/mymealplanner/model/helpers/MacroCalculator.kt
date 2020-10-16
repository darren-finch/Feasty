package com.darrenfinch.mymealplanner.model.helpers

import com.darrenfinch.mymealplanner.model.data.entities.Food
import com.darrenfinch.mymealplanner.model.data.entitysubdata.MacroNutrients
import com.darrenfinch.mymealplanner.domain.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.model.data.entities.MealFood

object MacroCalculator
{
    fun updateMacrosForFoodWithNewServingSize(food: Food, newServingSize: PhysicalQuantity): Food {
        val macros = food.macroNutrients
        val initialCalories = macros.calories
        val initialProtein = macros.protein
        val initialCarbs = macros.carbs
        val initialFat = macros.fat

        val caloriesPerUnit = food.servingSize.quantity / initialCalories
        val finalCalories = newServingSize.quantity / caloriesPerUnit

        val proteinPerUnit = food.servingSize.quantity / initialProtein
        val finalProtein = newServingSize.quantity / proteinPerUnit

        val carbsPerUnit = food.servingSize.quantity / initialCarbs
        val finalCarbs = newServingSize.quantity / carbsPerUnit

        val fatPerUnit = food.servingSize.quantity / initialFat
        val finalFat = newServingSize.quantity / fatPerUnit

        return Food(food.id, food.title, newServingSize, MacroNutrients(calories = finalCalories.toInt(), protein = finalProtein.toInt(), carbs = finalCarbs.toInt(),
            fat = finalFat.toInt()
        ))
    }

    fun updateMacrosForMealFoodWithNewServingSize(mealFood: MealFood, newServingSize: PhysicalQuantity): MealFood {
        val macros = mealFood.macroNutrients
        val initialCalories = macros.calories
        val initialProtein = macros.protein
        val initialCarbs = macros.carbs
        val initialFat = macros.fat

        val caloriesPerUnit = mealFood.desiredServingSize.quantity / initialCalories
        val finalCalories = newServingSize.quantity / caloriesPerUnit

        val proteinPerUnit = mealFood.desiredServingSize.quantity / initialProtein
        val finalProtein = newServingSize.quantity / proteinPerUnit

        val carbsPerUnit = mealFood.desiredServingSize.quantity / initialCarbs
        val finalCarbs = newServingSize.quantity / carbsPerUnit

        val fatPerUnit = mealFood.desiredServingSize.quantity / initialFat
        val finalFat = newServingSize.quantity / fatPerUnit

        return MealFood(mealFood.id, mealFood.title, newServingSize, MacroNutrients(calories = finalCalories.toInt(), protein = finalProtein.toInt(), carbs = finalCarbs.toInt(),
            fat = finalFat.toInt()
        ))
    }

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
        foods.forEach { food -> totalCarbohydrates += food.macroNutrients.carbs }
        return totalCarbohydrates
    }
    fun calculateTotalFat(foods: List<Food>) : Int
    {
        var totalFat = 0
        foods.forEach { food -> totalFat += food.macroNutrients.fat }
        return totalFat
    }
}