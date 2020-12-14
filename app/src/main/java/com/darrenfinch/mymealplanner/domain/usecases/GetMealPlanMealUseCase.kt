package com.darrenfinch.mymealplanner.domain.usecases

import androidx.lifecycle.LiveData
import com.darrenfinch.mymealplanner.model.MainRepository
import com.darrenfinch.mymealplanner.model.data.entities.MealPlanMeal

class GetMealPlanMealUseCase(private val repository: MainRepository) {
    fun getMealPlanMeal(id: Int): LiveData<MealPlanMeal> {
        return repository.getMealPlanMeal(id)
    }
}