package com.darrenfinch.mymealplanner.domain.usecases

import androidx.lifecycle.LiveData
import com.darrenfinch.mymealplanner.model.MainRepository
import com.darrenfinch.mymealplanner.model.data.entities.Meal

class GetAllMealsUseCase(private val repository: MainRepository) {
    fun fetchAllMeals(): LiveData<List<Meal>> {
        return repository.getMeals()
    }
}