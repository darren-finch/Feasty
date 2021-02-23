package com.darrenfinch.mymealplanner.foods.services

import com.darrenfinch.mymealplanner.foods.models.domain.MacroNutrients
import com.darrenfinch.mymealplanner.mealplans.models.domain.MealPlanMeal
import com.darrenfinch.mymealplanner.mealplans.models.mappers.uiMealPlanMealToMealPlanMeal
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlan
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlanMeal
import com.darrenfinch.mymealplanner.meals.models.domain.Meal
import com.darrenfinch.mymealplanner.meals.models.domain.MealFood
import com.darrenfinch.mymealplanner.meals.models.mappers.uiMealToMeal
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.screens.mealplan.MealPlanMacros

object MacroCalculatorService {

    /**
     * Takes some macro nutrients that are based on oldServingSize
     * and returns what the macro nutrients would be if they were based on newServingSize
     */
    fun baseMacrosOnDesiredServingSize(
        originalMacros: MacroNutrients,
        originalServingSize: PhysicalQuantity,
        desiredServingSize: PhysicalQuantity
    ): MacroNutrients {
        val originalCalories = originalMacros.calories
        val originalProteins = originalMacros.proteins
        val originalCarbs = originalMacros.carbs
        val originalFats = originalMacros.fats

        val caloriesPerUnit = originalServingSize.quantity / originalCalories
        val finalCalories = desiredServingSize.quantity / caloriesPerUnit

        val proteinsPerUnit = originalServingSize.quantity / originalProteins
        val finalProtein = desiredServingSize.quantity / proteinsPerUnit

        val carbsPerUnit = originalServingSize.quantity / originalCarbs
        val finalCarbs = desiredServingSize.quantity / carbsPerUnit

        val fatsPerUnit = originalServingSize.quantity / originalFats
        val finalFat = desiredServingSize.quantity / fatsPerUnit

        return MacroNutrients(
            calories = finalCalories.toInt(),
            proteins = finalProtein.toInt(),
            carbs = finalCarbs.toInt(),
            fats = finalFat.toInt()
        )
    }

    fun calculateMealMacros(meal: UiMeal): String {
        return calculateMealMacros(uiMealToMeal(meal))
    }

    fun calculateMealMacros(meal: Meal): String {
        return calculateMacrosFromMealFoods(meal.foods)
    }

    fun calculateMealMacros(mealPlanMeal: UiMealPlanMeal): String {
        return calculateMealMacros(uiMealPlanMealToMealPlanMeal(mealPlanMeal))
    }

    fun calculateMealMacros(mealPlanMeal: MealPlanMeal): String {
        return calculateMacrosFromMealFoods(mealPlanMeal.foods)
    }

    private fun calculateMacrosFromMealFoods(mealFoods: List<MealFood>): String {
        var totalCalories = 0
        var totalProteins = 0
        var totalCarbs = 0
        var totalFats = 0

        for(food in mealFoods) {
            val macrosBasedOnDesiredServingSize = baseMacrosOnDesiredServingSize(food.originalMacros, food.originalServingSize, food.desiredServingSize)
            totalCalories += macrosBasedOnDesiredServingSize.calories
            totalCarbs += macrosBasedOnDesiredServingSize.carbs
            totalFats += macrosBasedOnDesiredServingSize.fats
            totalProteins += macrosBasedOnDesiredServingSize.proteins
        }

        return MacroNutrients(
            totalCalories,
            totalCarbs,
            totalProteins,
            totalFats
        ).toString()
    }

    fun calculateMealPlanMacros(mealPlan: UiMealPlan, mealPlanMeals: List<UiMealPlanMeal>): MealPlanMacros {
        val totalCalories = mealPlanMeals.sumBy { mealPlanMeal ->
            mealPlanMeal.foods.sumBy { food -> food.originalMacros.calories }
        }
        val totalCarbs = mealPlanMeals.sumBy { mealPlanMeal ->
            mealPlanMeal.foods.sumBy { food -> food.originalMacros.carbs }
        }
        val totalFats = mealPlanMeals.sumBy { mealPlanMeal ->
            mealPlanMeal.foods.sumBy { food -> food.originalMacros.fats }
        }
        val totalProteins = mealPlanMeals.sumBy { mealPlanMeal ->
            mealPlanMeal.foods.sumBy { food -> food.originalMacros.proteins }
        }

        return MealPlanMacros(
            totalCalories = totalCalories,
            totalCarbs = totalCarbs,
            totalFats = totalFats,
            totalProteins = totalProteins,
            requiredCalories = mealPlan.requiredCalories,
            requiredCarbs = mealPlan.requiredCarbs,
            requiredFats = mealPlan.requiredFats,
            requiredProteins = mealPlan.requiredProteins
        )
    }
}