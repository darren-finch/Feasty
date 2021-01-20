package com.darrenfinch.mymealplanner.meals.usecases

import com.darrenfinch.mymealplanner.model.MainRepository

class DeleteAllMealsUseCase(private val repository: MainRepository) {
    fun deleteAllMeals() {
        repository.deleteAllMeals()
    }
}