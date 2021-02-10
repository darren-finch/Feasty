package com.darrenfinch.mymealplanner.meals.usecases

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal

class UpsertMealUseCase(private val insertMealUseCase: InsertMealUseCase, private val updateMealUseCase: UpdateMealUseCase) {
    suspend fun upsertMeal(meal: UiMeal) {
        if (meal.id == Constants.NEW_ITEM_ID) {
            insertMealUseCase.insertMeal(meal)
        } else {
            updateMealUseCase.updateMeal(meal)
        }
    }
}