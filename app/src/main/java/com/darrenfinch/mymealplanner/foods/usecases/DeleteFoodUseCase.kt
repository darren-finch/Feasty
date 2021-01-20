package com.darrenfinch.mymealplanner.foods.usecases

import com.darrenfinch.mymealplanner.model.MainRepository

class DeleteFoodUseCase(private val repository: MainRepository) {
    fun deleteFood(id: Int) {
        repository.deleteFood(id)
    }
}