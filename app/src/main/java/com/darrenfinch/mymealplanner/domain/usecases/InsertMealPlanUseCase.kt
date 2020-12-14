package com.darrenfinch.mymealplanner.domain.usecases

import com.darrenfinch.mymealplanner.model.MainRepository
import com.darrenfinch.mymealplanner.model.data.entities.MealPlan

class InsertMealPlanUseCase(private val repository: MainRepository) {
    fun insertMealPlan(mealPlan: MealPlan) {
        repository.insertMealPlan(mealPlan)
    }
}