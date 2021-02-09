package com.darrenfinch.mymealplanner.mealplans.usecases

import com.darrenfinch.mymealplanner.mealplans.models.mappers.dbMealPlanToMealPlan
import com.darrenfinch.mymealplanner.mealplans.models.mappers.mealPlanToUiMealPlan
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlan
import com.darrenfinch.mymealplanner.data.MainRepository

class GetAllMealPlansUseCase(private val repository: MainRepository) {
    suspend fun getAllMealPlans(): List<UiMealPlan> {
        return repository.getAllMealPlans().map {
            mealPlanToUiMealPlan(dbMealPlanToMealPlan(it))
        }
    }
}