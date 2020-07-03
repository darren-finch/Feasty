package com.darrenfinch.mymealplanner.domain.usecases

import com.darrenfinch.mymealplanner.model.FoodsRepository

class DeleteFoodUseCase(private val repository: FoodsRepository) {
    fun deleteFood(foodId: Int) {
        repository.deleteFood(foodId)
    }
}