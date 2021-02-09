package com.darrenfinch.mymealplanner.foods.usecases

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.foods.models.mappers.foodToDbFood
import com.darrenfinch.mymealplanner.foods.models.mappers.uiFoodToFood
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood
import com.darrenfinch.mymealplanner.data.MainRepository

class UpsertFoodUseCase(private val repository: MainRepository) {
    suspend fun upsertFood(food: UiFood) {
        if(food.id == Constants.NEW_ITEM_ID) {
            repository.insertFood(foodToDbFood(uiFoodToFood(food.copy(id = Constants.EXISTING_ITEM_ID))))
        } else {
            repository.updateFood(foodToDbFood(uiFoodToFood(food)))
        }
    }
}