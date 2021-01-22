package com.darrenfinch.mymealplanner.meals.usecases

import com.darrenfinch.mymealplanner.meals.models.mappers.mealFoodToDbMealFood
import com.darrenfinch.mymealplanner.meals.models.mappers.mealToDbMeal
import com.darrenfinch.mymealplanner.meals.models.mappers.uiMealToMeal
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import com.darrenfinch.mymealplanner.model.MainRepository

class InsertMealUseCase(private val repository: MainRepository) {
    suspend fun insertMeal(meal: UiMeal) {
        val regularMeal = uiMealToMeal(meal)
        val newMealId = repository.insertMeal(mealToDbMeal(regularMeal)).toInt()
        for (mealFood in regularMeal.foods) {
            val databaseMealFood = mealFoodToDbMealFood(mealFood).copy(mealId = newMealId)
            repository.insertMealFood(databaseMealFood)
        }
    }
}