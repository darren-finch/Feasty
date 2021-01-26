package com.darrenfinch.mymealplanner.foods.usecases

import com.darrenfinch.mymealplanner.foods.models.mappers.dbFoodToFood
import com.darrenfinch.mymealplanner.foods.models.mappers.foodToUiFood
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood
import com.darrenfinch.mymealplanner.model.MainRepository

class GetAllFoodsUseCase(private val repository: MainRepository) {
    suspend fun getAllFoods(): List<UiFood> {
        return repository.getAllFoods().map {
            foodToUiFood(dbFoodToFood(it))
        }
    }
}