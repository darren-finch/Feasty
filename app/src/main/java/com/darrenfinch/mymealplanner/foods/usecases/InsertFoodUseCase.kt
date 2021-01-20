package com.darrenfinch.mymealplanner.foods.usecases

import com.darrenfinch.mymealplanner.model.MainRepository
import com.darrenfinch.mymealplanner.foods.models.domain.Food

class InsertFoodUseCase(private val repository: MainRepository) {
    fun insertFood(food: Food) {
        repository.insertFood(food)
    }
}