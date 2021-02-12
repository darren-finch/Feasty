package com.darrenfinch.mymealplanner.mealplans.usecases

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.mealplans.models.mappers.mealPlanMealToDbMealPlanMeal
import com.darrenfinch.mymealplanner.mealplans.models.mappers.uiMealPlanMealToMealPlanMeal
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlanMeal
import com.darrenfinch.mymealplanner.data.MainRepository

class InsertMealPlanMealUseCase(private val repository: MainRepository) {
    suspend fun insertMealPlanMeal(mealPlanMeal: UiMealPlanMeal) {
        repository.insertMealPlanMeal(
            mealPlanMealToDbMealPlanMeal(
                uiMealPlanMealToMealPlanMeal(
                    mealPlanMeal.copy(id = Constants.EXISTING_ITEM_ID)
                )
            )
        )
    }
}