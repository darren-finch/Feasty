package com.darrenfinch.mymealplanner.domain.usecases

import com.darrenfinch.mymealplanner.model.FoodsRepository
import com.darrenfinch.mymealplanner.model.data.Food

class InsertFoodUseCase(private val repository: FoodsRepository) {
    fun insertFood(food: Food) {
        repository.insertFood(food)
    }
}