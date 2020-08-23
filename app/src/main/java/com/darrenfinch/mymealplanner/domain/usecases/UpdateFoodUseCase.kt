package com.darrenfinch.mymealplanner.domain.usecases

import com.darrenfinch.mymealplanner.model.MainRepository
import com.darrenfinch.mymealplanner.model.data.entities.Food

class UpdateFoodUseCase(private val repository: MainRepository) {
    fun updateFood(food: Food) {
        repository.updateFood(food)
    }
}