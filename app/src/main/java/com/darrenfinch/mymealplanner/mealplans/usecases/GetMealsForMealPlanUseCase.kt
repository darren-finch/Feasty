package com.darrenfinch.mymealplanner.mealplans.usecases

import androidx.lifecycle.LiveData
import com.darrenfinch.mymealplanner.mealplans.models.MealPlanMeal
import com.darrenfinch.mymealplanner.model.MainRepository

class GetMealsForMealPlanUseCase(private val repository: MainRepository) {
    fun getMealsForMealPlan(mealPlanId: Int): LiveData<List<MealPlanMeal>> {
        return repository.getMealsFromMealPlanId(mealPlanId)
    }
}