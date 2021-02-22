package com.darrenfinch.mymealplanner.foods.usecases

import com.darrenfinch.mymealplanner.data.MainRepository

class DeleteFoodUseCase(private val repository: MainRepository) {
    suspend fun deleteFood(id: Int) {
        repository.deleteFood(id)
    }
}