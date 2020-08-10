package com.darrenfinch.mymealplanner.domain.usecases

import com.darrenfinch.mymealplanner.model.MealsRepository

class DeleteAllMealsUseCase(private val repository: MealsRepository) {
    fun deleteAllMeals() {
        repository.deleteAllMeals()
    }
}