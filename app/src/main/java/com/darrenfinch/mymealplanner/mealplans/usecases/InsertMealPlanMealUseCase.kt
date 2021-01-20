package com.darrenfinch.mymealplanner.mealplans.usecases

import com.darrenfinch.mymealplanner.mealplans.models.domain.MealPlanMeal
import com.darrenfinch.mymealplanner.model.MainRepository

class InsertMealPlanMealUseCase(private val repository: MainRepository) {
    fun insertMealPlanMeal(mealPlanMeal: MealPlanMeal) {
        repository.insertMealPlanMeal(mealPlanMeal)
    }
}