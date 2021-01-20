package com.darrenfinch.mymealplanner.mealplans.usecases

import androidx.lifecycle.LiveData
import com.darrenfinch.mymealplanner.mealplans.models.MealPlan
import com.darrenfinch.mymealplanner.model.MainRepository

class GetMealPlanUseCase(private val repository: MainRepository) {
    fun getMealPlan(id: Int): LiveData<MealPlan> {
        return repository.getMealPlan(id)
    }
}