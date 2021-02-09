package com.darrenfinch.mymealplanner.mealplans.usecases

import com.darrenfinch.mymealplanner.data.MainRepository

class DeleteMealPlanMealUseCase(private val repository: MainRepository) {
    suspend fun deleteMealPlanMeal(id: Int) {
        repository.deleteMealPlanMeal(id)
    }
}