package com.darrenfinch.mymealplanner.domain.usecases

import androidx.lifecycle.LiveData
import com.darrenfinch.mymealplanner.model.MealsRepository
import com.darrenfinch.mymealplanner.model.data.Meal

class GetAllMealsUseCase(private val repository: MealsRepository) {
    fun fetchAllMeals() : LiveData<List<Meal>> {
        return repository.getMeals()
    }
}