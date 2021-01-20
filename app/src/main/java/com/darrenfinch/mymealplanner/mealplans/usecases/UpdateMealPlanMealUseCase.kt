package com.darrenfinch.mymealplanner.mealplans.usecases

import com.darrenfinch.mymealplanner.mealplans.models.MealPlanMeal
import com.darrenfinch.mymealplanner.model.MainRepository

class UpdateMealPlanMealUseCase(private val repository: MainRepository) {
    fun updateMealPlanMeal(mealPlanMeal: MealPlanMeal) {
        repository.updateMealPlanMeal(mealPlanMeal)
    }
}