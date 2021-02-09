package com.darrenfinch.mymealplanner.meals.usecases

import com.darrenfinch.mymealplanner.common.constants.DefaultModels
import com.darrenfinch.mymealplanner.meals.models.mappers.dbMealToMeal
import com.darrenfinch.mymealplanner.meals.models.mappers.mealToUiMeal
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import com.darrenfinch.mymealplanner.data.MainRepository

class GetMealUseCase(val repository: MainRepository) {
    suspend fun getMeal(id: Int): UiMeal {
        val dbMeal = repository.getMeal(id)
        return if(dbMeal != null) {
            val dbMealFoods = repository.getMealFoodsForMeal(id)
            val dbFoodReferences = dbMealFoods.map { dbMealFood -> repository.getFood(dbMealFood.foodId) }
            mealToUiMeal(dbMealToMeal(dbMeal, dbMealFoods, dbFoodReferences))
        }
        else {
            DefaultModels.defaultUiMeal
        }
    }
}