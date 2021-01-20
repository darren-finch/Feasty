package com.darrenfinch.mymealplanner.meals.usecases

import androidx.lifecycle.LiveData
import com.darrenfinch.mymealplanner.meals.models.domain.Meal
import com.darrenfinch.mymealplanner.model.MainRepository

class GetMealUseCase(val repository: MainRepository) {
    fun getMeal(id: Int): LiveData<Meal> {
        return repository.getMeal(id)
    }
}