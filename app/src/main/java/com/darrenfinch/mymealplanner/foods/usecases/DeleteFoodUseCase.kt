package com.darrenfinch.mymealplanner.foods.usecases

import com.darrenfinch.mymealplanner.data.MainRepository
import com.darrenfinch.mymealplanner.meals.models.domain.MealFood
import com.darrenfinch.mymealplanner.meals.models.mappers.dbMealFoodToMealFood

class DeleteFoodUseCase(private val repository: MainRepository) {
    suspend fun deleteFood(id: Int) {
        val invalidMealFoods = repository.getMealFoodsForFood(id)
        invalidMealFoods.forEach { invalidMealFood ->
            repository.deleteMealFood(invalidMealFood.id)
        }
        repository.deleteFood(id)
    }
}