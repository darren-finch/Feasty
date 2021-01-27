package com.darrenfinch.mymealplanner.mealplans.usecases

import com.darrenfinch.mymealplanner.model.MainRepository

class DeleteMealPlanUseCase(private val repository: MainRepository) {
    suspend fun deleteMealPlan(id: Int) {
        repository.deleteMealPlan(id)

        // TODO: Make sure meal plan meals are deleted too
    }
}