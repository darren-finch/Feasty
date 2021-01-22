package com.darrenfinch.mymealplanner.foods.usecases

import com.darrenfinch.mymealplanner.foods.models.mappers.foodToDbFood
import com.darrenfinch.mymealplanner.foods.models.mappers.uiFoodToFood
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood
import com.darrenfinch.mymealplanner.model.MainRepository

class InsertFoodUseCase(private val repository: MainRepository) {
    suspend fun insertFood(food: UiFood) {
        repository.insertFood(foodToDbFood(uiFoodToFood(food)))
    }
}