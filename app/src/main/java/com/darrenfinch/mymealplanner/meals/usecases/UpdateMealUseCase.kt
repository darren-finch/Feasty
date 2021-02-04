package com.darrenfinch.mymealplanner.meals.usecases

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.meals.models.domain.MealFood
import com.darrenfinch.mymealplanner.meals.models.mappers.mealFoodToDbMealFood
import com.darrenfinch.mymealplanner.meals.models.mappers.mealToDbMeal
import com.darrenfinch.mymealplanner.meals.models.mappers.uiMealToMeal
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import com.darrenfinch.mymealplanner.model.MainRepository
import com.darrenfinch.mymealplanner.model.room.models.meals.DatabaseMealFood

class UpdateMealUseCase(private val repository: MainRepository) {
    suspend fun updateMeal(meal: UiMeal) {
        val newMeal = uiMealToMeal(meal)
        val updatedMealFoods = newMeal.foods

        val oldDbMealFoods = repository.getMealFoodsForMeal(newMeal.id)

        insertNewMealFoods(updatedMealFoods, newMeal.id)
        updateEditedMealFoods(oldDbMealFoods, updatedMealFoods)
        deleteOldMealFoodsThatAreNotInUpdatedMealFoods(oldDbMealFoods.map { it.id }.toIntArray(), updatedMealFoods.map { it.id }.toIntArray())

        repository.updateMeal(mealToDbMeal(newMeal))
    }

    private suspend fun insertNewMealFoods(updatedMealFoods: List<MealFood>, mealId: Int) {
        updatedMealFoods.forEach {
            if(it.id == Constants.INVALID_ID) {
                repository.insertMealFood(mealFoodToDbMealFood(it.copy(id = Constants.VALID_ID, mealId = mealId)))
            }
        }
    }

    private suspend fun updateEditedMealFoods(oldMealFoods: List<DatabaseMealFood>, updatedMealFoods: List<MealFood>) {
        val oldMealFoodIds = oldMealFoods.map { it.id }
        val updatedMealFoodIds = updatedMealFoods.map { it.id }
        val editedMealFoodIds = oldMealFoodIds intersect updatedMealFoodIds

        editedMealFoodIds.forEach { editedMealFoodId ->
            val editedMealFood = updatedMealFoods.find { it.id == editedMealFoodId }
            editedMealFood?.let { repository.updateMealFood(mealFoodToDbMealFood(it) ) }
        }
    }

    private suspend fun deleteOldMealFoodsThatAreNotInUpdatedMealFoods(oldMealFoodIds: IntArray, updatedMealFoodsIds: IntArray) {
        val mealFoodsToDelete = oldMealFoodIds.toList() subtract updatedMealFoodsIds.toList()
        mealFoodsToDelete.forEach {
            repository.deleteMealFood(it)
        }
    }
}