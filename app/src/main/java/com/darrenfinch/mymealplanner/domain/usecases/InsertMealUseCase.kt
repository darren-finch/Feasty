package com.darrenfinch.mymealplanner.domain.usecases

import com.darrenfinch.mymealplanner.model.MainRepository
import com.darrenfinch.mymealplanner.model.data.entities.Meal

class InsertMealUseCase(private val repository: MainRepository) {
    fun insertMeal(meal: Meal) {
        repository.insertMeal(meal)
    }
}