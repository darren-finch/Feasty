package com.darrenfinch.mymealplanner.mealplans.usecases

import com.darrenfinch.mymealplanner.mealplans.models.mappers.mealPlanToDbMealPlan
import com.darrenfinch.mymealplanner.mealplans.models.mappers.uiMealPlanToMealPlan
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlan
import com.darrenfinch.mymealplanner.data.MainRepository

class UpdateMealPlanUseCase(private val repository: MainRepository) {
    suspend fun updateMealPlan(mealPlan: UiMealPlan) {
        repository.updateMealPlan(mealPlanToDbMealPlan(uiMealPlanToMealPlan(mealPlan)))
    }
}