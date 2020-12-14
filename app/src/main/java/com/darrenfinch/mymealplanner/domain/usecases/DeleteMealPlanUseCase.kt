package com.darrenfinch.mymealplanner.domain.usecases

import com.darrenfinch.mymealplanner.model.MainRepository
import com.darrenfinch.mymealplanner.model.data.entities.MealPlan

class DeleteMealPlanUseCase(private val repository: MainRepository) {
    fun deleteMealPlan(mealPlan: MealPlan) {
        repository.deleteMealPlan(mealPlan)
    }
}