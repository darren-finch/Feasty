package com.darrenfinch.mymealplanner.meals.usecases

import androidx.lifecycle.LiveData
import com.darrenfinch.mymealplanner.meals.models.domain.Meal
import com.darrenfinch.mymealplanner.model.MainRepository

class GetAllMealsUseCase(private val repository: MainRepository) {
    fun fetchAllMeals(): LiveData<List<Meal>> {
        return repository.getMeals()
    }
}