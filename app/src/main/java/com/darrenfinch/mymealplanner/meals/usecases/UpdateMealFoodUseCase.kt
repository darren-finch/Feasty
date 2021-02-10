package com.darrenfinch.mymealplanner.meals.usecases

import com.darrenfinch.mymealplanner.data.MainRepository
import com.darrenfinch.mymealplanner.meals.models.mappers.mealFoodToDbMealFood
import com.darrenfinch.mymealplanner.meals.models.mappers.uiMealFoodToMealFood
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood

class UpdateMealFoodUseCase(private val repository: MainRepository) {
    suspend fun updateMealFood(mealFood: UiMealFood) {
        repository.updateMealFood(mealFoodToDbMealFood(uiMealFoodToMealFood(mealFood)))
    }
}