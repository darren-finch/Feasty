package com.darrenfinch.mymealplanner.domain.usecases

import androidx.lifecycle.LiveData
import com.darrenfinch.mymealplanner.model.MainRepository
import com.darrenfinch.mymealplanner.model.data.entities.MealPlan

class GetMealPlanUseCase(private val repository: MainRepository) {
    fun getMealPlan(id: Int): LiveData<MealPlan> {
        return repository.getMealPlan(id)
    }
}