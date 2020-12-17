package com.darrenfinch.mymealplanner.domain.usecases

import com.darrenfinch.mymealplanner.model.MainRepository
import com.darrenfinch.mymealplanner.model.data.entities.MealPlanMeal

class DeleteMealPlanMealUseCase(private val repository: MainRepository) {
    fun deleteMealPlanMeal(mealPlanMeal: MealPlanMeal) {
        repository.deleteMealPlanMeal(mealPlanMeal)
    }
}