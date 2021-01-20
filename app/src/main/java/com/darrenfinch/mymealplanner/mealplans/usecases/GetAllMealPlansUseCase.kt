package com.darrenfinch.mymealplanner.mealplans.usecases

import androidx.lifecycle.LiveData
import com.darrenfinch.mymealplanner.mealplans.models.MealPlan
import com.darrenfinch.mymealplanner.model.MainRepository

class GetAllMealPlansUseCase(private val repository: MainRepository) {
    fun getMealPlans(): LiveData<List<MealPlan>> {
        return repository.getMealPlans()
    }
}