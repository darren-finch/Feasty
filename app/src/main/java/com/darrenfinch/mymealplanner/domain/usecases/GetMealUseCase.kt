package com.darrenfinch.mymealplanner.domain.usecases

import androidx.lifecycle.LiveData
import com.darrenfinch.mymealplanner.model.MainRepository
import com.darrenfinch.mymealplanner.model.data.entities.Meal

class GetMealUseCase(val repository: MainRepository) {
    fun getMeal(mealId: Int): LiveData<Meal> {
        return repository.getMeal(mealId)
    }
}