package com.darrenfinch.mymealplanner.domain.usecases

import androidx.lifecycle.LiveData
import com.darrenfinch.mymealplanner.model.MealsRepository
import com.darrenfinch.mymealplanner.model.data.Meal

class GetMealUseCase(val repository: MealsRepository) {
    fun getMeal(mealId: Int) : LiveData<Meal> {
        return repository.getMeal(mealId)
    }
}