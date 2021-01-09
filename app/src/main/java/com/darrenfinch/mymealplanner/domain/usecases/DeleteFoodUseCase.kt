package com.darrenfinch.mymealplanner.domain.usecases

import com.darrenfinch.mymealplanner.model.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class DeleteFoodUseCase(private val repository: MainRepository) {
    fun deleteFood(id: Int) {
        runBlocking (Dispatchers.IO) {
            val invalidMealFoods = repository.getMealFoodsFromFoodId(id)
            for (invalidMealFood in invalidMealFoods) {
                val mealToBeUpdated = repository.getMealSuspended(invalidMealFood.mealId)
                val mealFoods = mealToBeUpdated.foods.toMutableList()
                println("-------------------------------------------\nUpdating $mealToBeUpdated")
                mealFoods.remove(mealFoods.find { it.foodId == id })
                val newMeal = mealToBeUpdated.copy(foods = mealFoods)
                println("-------------------------------------------\nAfter updating $newMeal")
                repository.updateMeal(newMeal)
                repository.deleteMealFood(invalidMealFood.id)
            }
            repository.deleteFood(id)
        }
    }
}