package com.darrenfinch.mymealplanner.meals.usecases

import com.darrenfinch.mymealplanner.meals.models.Meal
import com.darrenfinch.mymealplanner.model.MainRepository

class UpdateMealUseCase(private val repository: MainRepository) {
    fun updateMeal(meal: Meal) {
        repository.updateMeal(meal)
    }
}