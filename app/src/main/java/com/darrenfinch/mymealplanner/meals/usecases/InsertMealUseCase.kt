package com.darrenfinch.mymealplanner.meals.usecases

import com.darrenfinch.mymealplanner.meals.models.mappers.mealToDbMeal
import com.darrenfinch.mymealplanner.meals.models.mappers.uiMealToMeal
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import com.darrenfinch.mymealplanner.data.MainRepository

class InsertMealUseCase(private val repository: MainRepository, private val insertMealFoodUseCase: InsertMealFoodUseCase) {
    suspend fun insertMeal(meal: UiMeal) {
        val dbMeal = mealToDbMeal(uiMealToMeal(meal))
        val idOfInsertedMeal = repository.insertMeal(dbMeal).toInt()
        meal.foods.forEach {
            insertMealFoodUseCase.insertMealFood(it, idOfInsertedMeal)
        }
    }
}