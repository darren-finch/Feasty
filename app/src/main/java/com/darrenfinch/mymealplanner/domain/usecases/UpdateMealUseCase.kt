package com.darrenfinch.mymealplanner.domain.usecases

import com.darrenfinch.mymealplanner.model.MainRepository
import com.darrenfinch.mymealplanner.model.data.entities.Meal

class UpdateMealUseCase(private val repository: MainRepository) {
    fun updateMeal(meal: Meal) {
        repository.updateMeal(meal)
    }
}