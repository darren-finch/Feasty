package com.darrenfinch.mymealplanner.foods.usecases

import com.darrenfinch.mymealplanner.common.constants.DefaultModels
import com.darrenfinch.mymealplanner.foods.models.mappers.dbFoodToFood
import com.darrenfinch.mymealplanner.foods.models.mappers.foodToUiFood
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood
import com.darrenfinch.mymealplanner.model.MainRepository

class GetFoodUseCase(private val repository: MainRepository) {
    suspend fun getFood(id: Int): UiFood {
        val foodFromDb = repository.getFood(id)
        return if(foodFromDb != null)
            foodToUiFood(dbFoodToFood(foodFromDb))
        else
            DefaultModels.defaultUiFood
    }
}