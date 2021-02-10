package com.darrenfinch.mymealplanner.meals.usecases

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.data.MainRepository
import com.darrenfinch.mymealplanner.meals.models.mappers.mealFoodToDbMealFood
import com.darrenfinch.mymealplanner.meals.models.mappers.uiMealFoodToMealFood
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood

class UpsertMealFoodUseCase(private val repository: MainRepository) {
    suspend fun upsertMealFood(mealFood: UiMealFood) {
        if(mealFood.id == Constants.NEW_ITEM_ID) {
            repository.insertMealFood(mealFoodToDbMealFood(uiMealFoodToMealFood(mealFood)))
        } else {
            repository.updateMealFood(mealFoodToDbMealFood(uiMealFoodToMealFood(mealFood)))
        }
    }
}