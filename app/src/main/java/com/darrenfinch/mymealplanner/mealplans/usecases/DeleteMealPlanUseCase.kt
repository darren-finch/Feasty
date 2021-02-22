package com.darrenfinch.mymealplanner.mealplans.usecases

import com.darrenfinch.mymealplanner.data.MainRepository

class DeleteMealPlanUseCase(private val repository: MainRepository) {
    suspend fun deleteMealPlan(id: Int) {
        repository.deleteMealPlan(id)
    }
}