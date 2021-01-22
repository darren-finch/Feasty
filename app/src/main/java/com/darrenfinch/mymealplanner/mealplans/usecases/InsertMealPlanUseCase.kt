package com.darrenfinch.mymealplanner.mealplans.usecases

import com.darrenfinch.mymealplanner.mealplans.models.domain.MealPlan
import com.darrenfinch.mymealplanner.mealplans.models.mappers.mealPlanToDbMealPlan
import com.darrenfinch.mymealplanner.mealplans.models.mappers.uiMealPlanToMealPlan
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlan
import com.darrenfinch.mymealplanner.model.MainRepository

class InsertMealPlanUseCase(private val repository: MainRepository) {
    suspend fun insertMealPlan(mealPlan: UiMealPlan) {
        repository.insertMealPlan(mealPlanToDbMealPlan(uiMealPlanToMealPlan(mealPlan)))
    }
}