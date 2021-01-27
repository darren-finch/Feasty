package com.darrenfinch.mymealplanner.mealplans.usecases

import com.darrenfinch.mymealplanner.model.MainRepository

class DeleteMealPlanUseCase(private val repository: MainRepository) {
    suspend fun deleteMealPlan(id: Int) {
        repository.getMealsForMealPlan(id).forEach {
            repository.deleteMealPlanMeal(it.id)
        }
        repository.deleteMealPlan(id)
    }
}