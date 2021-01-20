package com.darrenfinch.mymealplanner.mealplans.usecases

import com.darrenfinch.mymealplanner.mealplans.models.domain.MealPlan
import com.darrenfinch.mymealplanner.model.MainRepository

class UpdateMealPlanUseCase(private val repository: MainRepository) {
    fun updateMealPlan(mealPlan: MealPlan) {
        repository.updateMealPlan(mealPlan)
    }
}