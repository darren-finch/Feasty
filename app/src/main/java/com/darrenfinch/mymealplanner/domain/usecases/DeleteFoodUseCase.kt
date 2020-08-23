package com.darrenfinch.mymealplanner.domain.usecases

import com.darrenfinch.mymealplanner.model.MainRepository

class DeleteFoodUseCase(private val repository: MainRepository) {
    fun deleteFood(foodId: Int) {
        repository.deleteFood(foodId)
    }
}