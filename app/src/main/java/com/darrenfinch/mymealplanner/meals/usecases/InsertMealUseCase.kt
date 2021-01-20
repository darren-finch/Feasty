package com.darrenfinch.mymealplanner.meals.usecases

import com.darrenfinch.mymealplanner.meals.models.Meal
import com.darrenfinch.mymealplanner.model.MainRepository

class InsertMealUseCase(private val repository: MainRepository) {
    fun insertMeal(meal: Meal) {
        repository.insertMeal(meal)
    }
}