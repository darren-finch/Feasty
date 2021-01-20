package com.darrenfinch.mymealplanner.foods.usecases

import com.darrenfinch.mymealplanner.model.MainRepository
import com.darrenfinch.mymealplanner.foods.models.Food

class UpdateFoodUseCase(private val repository: MainRepository) {
    fun updateFood(food: Food) {
        repository.updateFood(food)
    }
}