package com.darrenfinch.mymealplanner.mealplans.usecases

import com.darrenfinch.mymealplanner.mealplans.models.mappers.dbMealPlanToMealPlan
import com.darrenfinch.mymealplanner.mealplans.models.mappers.mealPlanToUiMealPlan
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlan
import com.darrenfinch.mymealplanner.model.MainRepository

class GetMealPlanUseCase(private val repository: MainRepository) {
    suspend fun getMealPlan(id: Int): UiMealPlan {
        return mealPlanToUiMealPlan(dbMealPlanToMealPlan(repository.getMealPlan(id)))
    }
}