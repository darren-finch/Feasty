package com.darrenfinch.mymealplanner.meals.usecases

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.meals.models.mappers.mealFoodToDbMealFood
import com.darrenfinch.mymealplanner.meals.models.mappers.mealToDbMeal
import com.darrenfinch.mymealplanner.meals.models.mappers.uiMealToMeal
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import com.darrenfinch.mymealplanner.model.MainRepository

class InsertMealUseCase(private val repository: MainRepository) {
    suspend fun insertMeal(meal: UiMeal) {
        val regularMeal = uiMealToMeal(meal)
        val newMealId = repository.insertMeal(mealToDbMeal(regularMeal)).toInt()
        regularMeal.foods.forEach {
            if(it.id == Constants.NEW_ITEM_ID) {
                repository.insertMealFood(mealFoodToDbMealFood(it.copy(id = Constants.VALID_ID, mealId = newMealId)))
            }
        }
    }
}