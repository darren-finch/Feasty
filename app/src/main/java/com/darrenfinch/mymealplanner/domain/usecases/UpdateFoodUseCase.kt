package com.darrenfinch.mymealplanner.domain.usecases

import com.darrenfinch.mymealplanner.model.FoodsRepository
import com.darrenfinch.mymealplanner.model.data.Food

class UpdateFoodUseCase(private val repository: FoodsRepository) {
    fun updateFood(food: Food) {
        repository.updateFood(food)
    }
}