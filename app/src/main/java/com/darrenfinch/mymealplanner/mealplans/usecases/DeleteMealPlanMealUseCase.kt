package com.darrenfinch.mymealplanner.mealplans.usecases

import com.darrenfinch.mymealplanner.mealplans.models.domain.MealPlanMeal
import com.darrenfinch.mymealplanner.model.MainRepository

class DeleteMealPlanMealUseCase(private val repository: MainRepository) {
    suspend fun deleteMealPlanMeal(id: Int) {
        repository.deleteMealPlanMeal(id)
    }
}