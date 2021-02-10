package com.darrenfinch.mymealplanner.meals.usecases

import com.darrenfinch.mymealplanner.data.MainRepository

class DeleteMealFoodUseCase(private val repository: MainRepository) {
    suspend fun deleteMealFood(id: Int) {
        repository.deleteMealFood(id)
    }
}