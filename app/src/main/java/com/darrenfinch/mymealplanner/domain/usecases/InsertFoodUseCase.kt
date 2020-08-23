package com.darrenfinch.mymealplanner.domain.usecases

import com.darrenfinch.mymealplanner.model.MainRepository
import com.darrenfinch.mymealplanner.model.data.entities.Food

class InsertFoodUseCase(private val repository: MainRepository) {
    fun insertFood(food: Food) {
        repository.insertFood(food)
    }
}