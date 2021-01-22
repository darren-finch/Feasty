package com.darrenfinch.mymealplanner.foods.usecases

import com.darrenfinch.mymealplanner.foods.models.mappers.dbFoodToFood
import com.darrenfinch.mymealplanner.foods.models.mappers.foodToUiFood
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood
import com.darrenfinch.mymealplanner.model.MainRepository

class GetFoodUseCase(private val repository: MainRepository) {
    suspend fun getFood(id: Int): UiFood {
        return foodToUiFood(dbFoodToFood(repository.getFood(id)))
    }
}