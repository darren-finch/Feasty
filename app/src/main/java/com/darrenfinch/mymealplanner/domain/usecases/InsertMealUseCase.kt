package com.darrenfinch.mymealplanner.domain.usecases

import com.darrenfinch.mymealplanner.model.MealsRepository
import com.darrenfinch.mymealplanner.model.data.Meal

class InsertMealUseCase(private val repository: MealsRepository) {
    fun insertMeal(meal: Meal) {
        repository.insertMeal(meal)
    }
}