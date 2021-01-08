package com.darrenfinch.mymealplanner.domain.usecases

import androidx.lifecycle.LiveData
import com.darrenfinch.mymealplanner.model.MainRepository
import com.darrenfinch.mymealplanner.model.data.entities.MealPlanMeal

class GetMealsForMealPlanUseCase(private val repository: MainRepository) {
    fun getMealsForMealPlan(mealPlanId: Int): LiveData<List<MealPlanMeal>> {
        return repository.getMealsFromMealPlanId(mealPlanId)
    }
}