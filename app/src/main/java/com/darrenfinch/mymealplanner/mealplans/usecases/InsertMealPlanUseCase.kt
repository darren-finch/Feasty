package com.darrenfinch.mymealplanner.mealplans.usecases

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.mealplans.models.mappers.mealPlanToDbMealPlan
import com.darrenfinch.mymealplanner.mealplans.models.mappers.uiMealPlanToMealPlan
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlan
import com.darrenfinch.mymealplanner.data.MainRepository

class InsertMealPlanUseCase(private val repository: MainRepository) {
    suspend fun insertMealPlan(mealPlan: UiMealPlan) {
        repository.insertMealPlan(mealPlanToDbMealPlan(uiMealPlanToMealPlan(mealPlan.copy(id = Constants.EXISTING_ITEM_ID))))
    }
}