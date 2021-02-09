package com.darrenfinch.mymealplanner.mealplans.usecases

import com.darrenfinch.mymealplanner.mealplans.models.mappers.mealPlanMealToDbMealPlanMeal
import com.darrenfinch.mymealplanner.mealplans.models.mappers.uiMealPlanMealToMealPlanMeal
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlanMeal
import com.darrenfinch.mymealplanner.data.MainRepository

class UpdateMealPlanMealUseCase(private val repository: MainRepository) {
    suspend fun updateMealPlanMeal(mealPlanMeal: UiMealPlanMeal) {
        repository.updateMealPlanMeal(mealPlanMealToDbMealPlanMeal(uiMealPlanMealToMealPlanMeal(mealPlanMeal)))
    }
}