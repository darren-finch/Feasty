package com.darrenfinch.mymealplanner.domain.usecases

import com.darrenfinch.mymealplanner.model.MainRepository
import com.darrenfinch.mymealplanner.model.data.entities.MealPlan

class UpdateMealPlanUseCase(private val repository: MainRepository) {
    fun updateMealPlan(mealPlan: MealPlan) {
        repository.updateMealPlan(mealPlan)
    }
}