package com.darrenfinch.mymealplanner.foods.models.domain

import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.meals.models.domain.Meal
import com.darrenfinch.mymealplanner.mealplans.models.domain.MealPlanMeal
import com.darrenfinch.mymealplanner.mealplans.models.mappers.uiMealPlanMealToMealPlanMeal
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlanMeal
import com.darrenfinch.mymealplanner.meals.models.mappers.uiMealToMeal
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal

object MacroCalculator {

    /**
     * Takes some macro nutrients that are based on oldServingSize
     * and returns what the macro nutrients would be if they were based on newServingSize
     */
    fun baseMacrosOnNewServingSize(
        oldMacros: MacroNutrients,
        oldServingSize: PhysicalQuantity,
        newServingSize: PhysicalQuantity
    ): MacroNutrients {
        val initialCalories = oldMacros.calories
        val initialProteins = oldMacros.proteins
        val initialCarbs = oldMacros.carbs
        val initialFats = oldMacros.fats

        val caloriesPerUnit = oldServingSize.quantity / initialCalories
        val finalCalories = newServingSize.quantity / caloriesPerUnit

        val proteinsPerUnit = oldServingSize.quantity / initialProteins
        val finalProtein = newServingSize.quantity / proteinsPerUnit

        val carbsPerUnit = oldServingSize.quantity / initialCarbs
        val finalCarbs = newServingSize.quantity / carbsPerUnit

        val fatsPerUnit = oldServingSize.quantity / initialFats
        val finalFat = newServingSize.quantity / fatsPerUnit

        return MacroNutrients(
            calories = finalCalories.toInt(),
            proteins = finalProtein.toInt(),
            carbs = finalCarbs.toInt(),
            fats = finalFat.toInt()
        )
    }

    fun calculateMealMacroNutrients(meal: UiMeal): String {
        return calculateMealMacroNutrients(uiMealToMeal(meal))
    }

    fun calculateMealMacroNutrients(meal: Meal): String {
        var totalCalories = 0
        var totalProteins = 0
        var totalCarbohydrates = 0
        var totalFats = 0

        meal.foods.forEach { food -> totalCalories += food.macroNutrients.calories }
        meal.foods.forEach { food -> totalProteins += food.macroNutrients.proteins }
        meal.foods.forEach { food -> totalCarbohydrates += food.macroNutrients.carbs }
        meal.foods.forEach { food -> totalFats += food.macroNutrients.fats }

        return MacroNutrients(
            totalCalories,
            totalCarbohydrates,
            totalProteins,
            totalFats
        ).toString()
    }

    fun calculateMealMacroNutrients(mealPlanMeal: UiMealPlanMeal): String {
        return calculateMealMacroNutrients(uiMealPlanMealToMealPlanMeal(mealPlanMeal))
    }

    fun calculateMealMacroNutrients(mealPlanMeal: MealPlanMeal): String {
        var totalCalories = 0
        var totalProteins = 0
        var totalCarbohydrates = 0
        var totalFats = 0

        mealPlanMeal.foods.forEach { food -> totalCalories += food.macroNutrients.calories }
        mealPlanMeal.foods.forEach { food -> totalProteins += food.macroNutrients.proteins }
        mealPlanMeal.foods.forEach { food -> totalCarbohydrates += food.macroNutrients.carbs }
        mealPlanMeal.foods.forEach { food -> totalFats += food.macroNutrients.fats }

        return MacroNutrients(
            totalCalories,
            totalCarbohydrates,
            totalProteins,
            totalFats
        ).toString()
    }
}