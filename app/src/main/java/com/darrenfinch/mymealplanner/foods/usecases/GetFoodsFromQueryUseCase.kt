package com.darrenfinch.mymealplanner.foods.usecases

import com.darrenfinch.mymealplanner.data.MainRepository
import com.darrenfinch.mymealplanner.foods.models.mappers.dbFoodToFood
import com.darrenfinch.mymealplanner.foods.models.mappers.foodToUiFood
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood

class GetFoodsFromQueryUseCase(private val repository: MainRepository) {
    suspend fun getFoodsFromQuery(query: String): List<UiFood> {
        return if (query.isEmpty()) {
            repository.getAllFoods().map {
                foodToUiFood(dbFoodToFood(it))
            }
        } else {
            repository.getFoodsFromQuery(query).map {
                foodToUiFood(dbFoodToFood(it))
            }
        }
    }
}