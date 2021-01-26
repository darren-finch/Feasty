package com.darrenfinch.mymealplanner.foods.usecases

import com.darrenfinch.mymealplanner.meals.models.domain.Meal
import com.darrenfinch.mymealplanner.meals.models.domain.MealFood
import com.darrenfinch.mymealplanner.meals.models.mappers.dbMealFoodToMealFood
import com.darrenfinch.mymealplanner.meals.models.mappers.dbMealToMeal
import com.darrenfinch.mymealplanner.meals.models.mappers.mealToDbMeal
import com.darrenfinch.mymealplanner.model.MainRepository

class DeleteFoodUseCase(private val repository: MainRepository) {
    suspend fun deleteFood(id: Int) {
        val invalidMealFoods = getInvalidMealFoods(id)
        for (invalidMealFood in invalidMealFoods) {
            val mealToBeUpdated = getMeal(invalidMealFood.mealId)
            val newMeal = mealToBeUpdated?.copy(foods = mealToBeUpdated.foods - invalidMealFood)
            newMeal?.let{ repository.updateMeal(mealToDbMeal(newMeal)) }
            repository.deleteMealFood(invalidMealFood.id)
        }
        repository.deleteFood(id)
    }

    private suspend fun getInvalidMealFoods(foodId: Int): List<MealFood> {
        return repository.getMealFoodsForFood(foodId).mapNotNull {
            repository.getFood(it.foodId)?.let { it1 -> dbMealFoodToMealFood(it, it1) }
        }
    }

    private suspend fun getMeal(id: Int): Meal? {
        val dbMeal = repository.getMeal(id)
        val dbMealFoods = repository.getMealFoodsForMeal(id)
        val dbFoodReferences =
            dbMealFoods.mapNotNull { dbMealFood -> repository.getFood(dbMealFood.foodId) }
        return dbMeal?.let { dbMealToMeal(it, dbMealFoods, dbFoodReferences) }
    }
}