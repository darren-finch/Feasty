package com.darrenfinch.mymealplanner.domain.usecases

import com.darrenfinch.mymealplanner.model.MainRepository
import com.darrenfinch.mymealplanner.model.data.entities.MealPlanMeal

class UpdateMealPlanMealUseCase(private val repository: MainRepository) {
    fun updateMealPlanMeal(mealPlanMeal: MealPlanMeal) {
        repository.updateMealPlanMeal(mealPlanMeal)
    }
}