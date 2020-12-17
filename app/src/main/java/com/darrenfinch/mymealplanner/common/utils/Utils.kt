package com.darrenfinch.mymealplanner.common.utils

import com.darrenfinch.mymealplanner.model.data.entities.Meal
import com.darrenfinch.mymealplanner.model.data.entities.MealPlanMeal
import com.darrenfinch.mymealplanner.model.data.entitysubdata.MacroNutrients

object Utils {
    fun calculateTotalCalories(meal: Meal) = meal.foods.sumBy { it.macroNutrients.calories }
    fun calculateTotalCarbohydrates(meal: Meal) = meal.foods.sumBy { it.macroNutrients.carbs }
    fun calculateTotalFats(meal: Meal) = meal.foods.sumBy { it.macroNutrients.fat }
    fun calculateTotalProteins(meal: Meal) = meal.foods.sumBy { it.macroNutrients.protein }
    fun calculateTotalCalories(meal: MealPlanMeal) = meal.foods.sumBy { it.macroNutrients.calories }
    fun calculateTotalCarbohydrates(meal: MealPlanMeal) = meal.foods.sumBy { it.macroNutrients.carbs }
    fun calculateTotalFats(meal: MealPlanMeal) = meal.foods.sumBy { it.macroNutrients.fat }
    fun calculateTotalProteins(meal: MealPlanMeal) = meal.foods.sumBy { it.macroNutrients.protein }
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