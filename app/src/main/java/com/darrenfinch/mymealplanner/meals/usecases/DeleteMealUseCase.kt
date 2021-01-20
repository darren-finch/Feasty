package com.darrenfinch.mymealplanner.meals.usecases

import com.darrenfinch.mymealplanner.model.MainRepository

class DeleteMealUseCase(private val repository: MainRepository) {
    fun deleteMeal(id: Int) {
        repository.deleteMeal(id)
    }
}