package com.darrenfinch.mymealplanner.meals.usecases

import com.darrenfinch.mymealplanner.meals.models.mappers.dbMealFoodToMealFood
import com.darrenfinch.mymealplanner.meals.models.mappers.mealFoodToUiMealFood
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood
import com.darrenfinch.mymealplanner.data.MainRepository

class GetMealFoodUseCase(private val repository: MainRepository) {
    suspend fun getMealFood(mealFoodId: Int) : UiMealFood? {
        val dbMealFood = repository.getMealFood(mealFoodId)
        val dbFoodReference = dbMealFood?.foodId?.let { repository.getFood(it) }
        return if(dbFoodReference != null) {
            mealFoodToUiMealFood(dbMealFoodToMealFood(dbMealFood, dbFoodReference))
        } else {
            null
        }
    }
}