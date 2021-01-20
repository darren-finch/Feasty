package com.darrenfinch.mymealplanner.mealplans.usecases

import com.darrenfinch.mymealplanner.model.MainRepository

class DeleteMealPlanUseCase(private val repository: MainRepository) {
    fun deleteMealPlan(id: Int) {
        repository.deleteMealPlan(id)
    }
}