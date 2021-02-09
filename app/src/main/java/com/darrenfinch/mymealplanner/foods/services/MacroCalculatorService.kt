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
        var totalCarbohydrates = 0
        var totalFats = 0

        mealFoods.forEach { food -> totalCalories += food.updatedMacroNutrients.calories }
        mealFoods.forEach { food -> totalProteins += food.updatedMacroNutrients.proteins }
        mealFoods.forEach { food -> totalCarbohydrates += food.updatedMacroNutrients.carbs }
        mealFoods.forEach { food -> totalFats += food.updatedMacroNutrients.fats }

        return MacroNutrients(
            totalCalories,
            totalCarbohydrates,
            totalProteins,
            totalFats
        ).toString()
    }

    fun calculateMealPlanMacros(mealPlan: UiMealPlan, mealPlanMeals: List<UiMealPlanMeal>): MealPlanMacros {
        val totalCalories = mealPlanMeals.sumBy { mealPlanMeal ->
            mealPlanMeal.foods.sumBy { food -> food.originalMacroNutrients.calories }
        }
        val totalCarbs = mealPlanMeals.sumBy { mealPlanMeal ->
            mealPlanMeal.foods.sumBy { food -> food.originalMacroNutrients.carbs }
        }
        val totalFats = mealPlanMeals.sumBy { mealPlanMeal ->
            mealPlanMeal.foods.sumBy { food -> food.originalMacroNutrients.fats }
        }
        val totalProteins = mealPlanMeals.sumBy { mealPlanMeal ->
            mealPlanMeal.foods.sumBy { food -> food.originalMacroNutrients.proteins }
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