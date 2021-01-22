package com.darrenfinch.mymealplanner.meals.usecases

import com.darrenfinch.mymealplanner.meals.models.mappers.mealFoodToDbMealFood
import com.darrenfinch.mymealplanner.meals.models.mappers.mealToDbMeal
import com.darrenfinch.mymealplanner.meals.models.mappers.uiMealToMeal
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import com.darrenfinch.mymealplanner.model.MainRepository

class UpdateMealUseCase(private val repository: MainRepository) {
    suspend fun updateMeal(meal: UiMeal) {
        val regularMeal = uiMealToMeal(meal)
        regularMeal.foods.forEach {
            repository.insertMealFood(mealFoodToDbMealFood(it))
        }
        repository.updateMeal(mealToDbMeal(regularMeal))
    }
}