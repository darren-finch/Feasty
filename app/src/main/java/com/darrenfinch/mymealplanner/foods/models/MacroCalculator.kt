package com.darrenfinch.mymealplanner.foods.models

import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.meals.models.Meal
import com.darrenfinch.mymealplanner.meals.models.MealFood
import com.darrenfinch.mymealplanner.mealplans.models.MealPlanMeal

object MacroCalculator {
    fun updateMacrosForFoodWithNewServingSize(food: Food, newServingSize: PhysicalQuantity): Food {
        val macros = food.macroNutrients
        val initialCalories = macros.calories
        val initialProtein = macros.proteins
        val initialCarbs = macros.carbs
        val initialFat = macros.fats

        val caloriesPerUnit = food.servingSize.quantity / initialCalories
        val finalCalories = newServingSize.quantity / caloriesPerUnit

        val proteinPerUnit = food.servingSize.quantity / initialProtein
        val finalProtein = newServingSize.quantity / proteinPerUnit

        val carbsPerUnit = food.servingSize.quantity / initialCarbs
        val finalCarbs = newServingSize.quantity / carbsPerUnit

        val fatPerUnit = food.servingSize.quantity / initialFat
        val finalFat = newServingSize.quantity / fatPerUnit

        return Food(
            food.id, food.title, newServingSize, MacroNutrients(
                calories = finalCalories.toInt(), proteins = finalProtein.toInt(), carbs = finalCarbs.toInt(),
                fats = finalFat.toInt()
            )
        )
    }

    fun updateMacrosForMealFoodWithNewServingSize(mealFood: MealFood, newServingSize: PhysicalQuantity): MealFood {
        val macros = mealFood.macroNutrients
        val initialCalories = macros.calories
        val initialProtein = macros.proteins
        val initialCarbs = macros.carbs
        val initialFat = macros.fats

        val caloriesPerUnit = mealFood.desiredServingSize.quantity / initialCalories
        val finalCalories = newServingSize.quantity / caloriesPerUnit

        val proteinPerUnit = mealFood.desiredServingSize.quantity / initialProtein
        val finalProtein = newServingSize.quantity / proteinPerUnit

        val carbsPerUnit = mealFood.desiredServingSize.quantity / initialCarbs
        val finalCarbs = newServingSize.quantity / carbsPerUnit

        val fatPerUnit = mealFood.desiredServingSize.quantity / initialFat
        val finalFat = newServingSize.quantity / fatPerUnit

        return mealFood.copy(
            macroNutrients = MacroNutrients(
                calories = finalCalories.toInt(),
                proteins = finalProtein.toInt(),
                carbs = finalCarbs.toInt(),
                fats = finalFat.toInt()
            )
        )
    }

    fun calculateTotalCalories(meal: MealPlanMeal) = meal.foods.sumBy { it.macroNutrients.calories }
    fun calculateTotalCarbohydrates(meal: MealPlanMeal) = meal.foods.sumBy { it.macroNutrients.carbs }
    fun calculateTotalFats(meal: MealPlanMeal) = meal.foods.sumBy { it.macroNutrients.fats }
    fun calculateTotalProteins(meal: MealPlanMeal) = meal.foods.sumBy { it.macroNutrients.proteins }
    fun calculateMealMacroNutrients(meal: Meal): String {
        var totalCalories = 0
        var totalProteins = 0
        var totalCarbohydrates = 0
        var totalFats = 0

        meal.foods.forEach { food -> totalCalories += food.macroNutrients.calories }
        meal.foods.forEach { food -> totalProteins += food.macroNutrients.proteins }
        meal.foods.forEach { food -> totalCarbohydrates += food.macroNutrients.carbs }
        meal.foods.forEach { food -> totalFats += food.macroNutrients.fats }

        return MacroNutrients(totalCalories, totalCarbohydrates, totalProteins, totalFats).toString()
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

        return MacroNutrients(totalCalories, totalCarbohydrates, totalProteins, totalFats).toString()
    }
}