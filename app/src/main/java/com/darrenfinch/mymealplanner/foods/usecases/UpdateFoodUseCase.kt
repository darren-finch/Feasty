package com.darrenfinch.mymealplanner.foods.usecases

import com.darrenfinch.mymealplanner.model.MainRepository
import com.darrenfinch.mymealplanner.foods.models.domain.Food
import com.darrenfinch.mymealplanner.foods.models.mappers.foodToDbFood
import com.darrenfinch.mymealplanner.foods.models.mappers.uiFoodToFood
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood

class UpdateFoodUseCase(private val repository: MainRepository) {
    suspend fun updateFood(food: UiFood) {
        repository.updateFood(foodToDbFood(uiFoodToFood(food)))
    }
}