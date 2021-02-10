package com.darrenfinch.mymealplanner.meals.usecases

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.data.MainRepository
import com.darrenfinch.mymealplanner.meals.models.mappers.mealFoodToDbMealFood
import com.darrenfinch.mymealplanner.meals.models.mappers.uiMealFoodToMealFood
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood

class InsertMealFoodUseCase(private val repository: MainRepository) {
    suspend fun insertMealFood(mealFood: UiMealFood, mealId: Int) {
        val mealFoodToInsert = mealFood.copy(id = Constants.EXISTING_ITEM_ID, mealId = mealId)
        val dbMealFood = mealFoodToDbMealFood(uiMealFoodToMealFood(mealFoodToInsert))
        repository.insertMealFood(dbMealFood)
    }
}