package com.darrenfinch.mymealplanner.meals.usecases

import com.darrenfinch.mymealplanner.data.MainRepository

class DeleteMealUseCase(private val repository: MainRepository) {
    suspend fun deleteMeal(id: Int) {
        val mealFoods = repository.getMealFoodsForMeal(id)
        mealFoods.forEach {
            repository.deleteMealFood(it.id)
        }
        repository.deleteMeal(id)
    }
}