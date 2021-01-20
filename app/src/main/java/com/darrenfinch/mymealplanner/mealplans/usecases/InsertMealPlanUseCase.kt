package com.darrenfinch.mymealplanner.mealplans.usecases

import com.darrenfinch.mymealplanner.mealplans.models.domain.MealPlan
import com.darrenfinch.mymealplanner.model.MainRepository

class InsertMealPlanUseCase(private val repository: MainRepository) {
    fun insertMealPlan(mealPlan: MealPlan) {
        repository.insertMealPlan(mealPlan)
    }
}