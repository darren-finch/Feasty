package com.darrenfinch.mymealplanner.mealplans.usecases

import com.darrenfinch.mymealplanner.mealplans.models.domain.MealPlanMeal
import com.darrenfinch.mymealplanner.model.MainRepository

class DeleteMealPlanMealUseCase(private val repository: MainRepository) {
    fun deleteMealPlanMeal(mealPlanMeal: MealPlanMeal) {
        repository.deleteMealPlanMeal(mealPlanMeal)
    }
}