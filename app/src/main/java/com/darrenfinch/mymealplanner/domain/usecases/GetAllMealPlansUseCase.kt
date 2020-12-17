package com.darrenfinch.mymealplanner.domain.usecases

import androidx.lifecycle.LiveData
import com.darrenfinch.mymealplanner.model.MainRepository
import com.darrenfinch.mymealplanner.model.data.entities.MealPlan

class GetAllMealPlansUseCase(private val repository: MainRepository) {
    fun getMealPlans(): LiveData<List<MealPlan>> {
        return repository.getMealPlans()
    }
}