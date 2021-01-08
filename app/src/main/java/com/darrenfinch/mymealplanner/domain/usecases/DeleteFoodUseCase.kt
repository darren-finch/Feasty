package com.darrenfinch.mymealplanner.domain.usecases

import com.darrenfinch.mymealplanner.model.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class DeleteFoodUseCase(private val repository: MainRepository) {

    // TODO: PLEASE, for PETE'S SAKE, MOVE TO KOTLIN FLOW, AND REMOVE THIS LIFECYCLE OWNER GARBAGE!
    fun deleteFood(id: Int) {
        runBlocking (Dispatchers.IO) {
            repository.deleteFood(id)
            val invalidMealFoods = repository.getMealFoodsFromFoodId(id)
            for (invalidMealFood in invalidMealFoods) {
                val mealToBeUpdated = repository.getMealSuspended(invalidMealFood.foodId)
                val mealFoods = mealToBeUpdated.foods.toMutableList()
                mealFoods.remove(mealFoods.find { it.foodId == id })
                val newMeal = mealToBeUpdated.copy(foods = mealFoods)
                repository.updateMeal(newMeal)
            }
        }
    }
}