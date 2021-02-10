package com.darrenfinch.mymealplanner.meals.usecases

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.meals.models.domain.MealFood
import com.darrenfinch.mymealplanner.meals.models.mappers.mealFoodToDbMealFood
import com.darrenfinch.mymealplanner.meals.models.mappers.mealToDbMeal
import com.darrenfinch.mymealplanner.meals.models.mappers.uiMealToMeal
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import com.darrenfinch.mymealplanner.data.MainRepository
import com.darrenfinch.mymealplanner.data.room.models.meals.DbMealFood
import com.darrenfinch.mymealplanner.meals.models.mappers.uiMealFoodToMealFood
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood

class UpdateMealUseCase(
    private val repository: MainRepository,
    private val insertMealFoodUseCase: InsertMealFoodUseCase,
    private val updateMealFoodUseCase: UpdateMealFoodUseCase
) {
    suspend fun updateMeal(meal: UiMeal) {
        val updatedMealFoods = meal.foods

        val oldDbMealFoods = repository.getMealFoodsForMeal(meal.id)

        insertNewMealFoods(updatedMealFoods, meal.id)
        updateEditedMealFoods(oldDbMealFoods, updatedMealFoods)
        deleteOldMealFoodsThatAreNotInUpdatedMealFoods(
            oldDbMealFoods.map { it.id }.toIntArray(),
            updatedMealFoods.map { it.id }.toIntArray()
        )

        repository.updateMeal(mealToDbMeal(uiMealToMeal(meal)))
    }

    private suspend fun insertNewMealFoods(updatedMealFoods: List<UiMealFood>, mealId: Int) {
        updatedMealFoods.forEach {
            if (it.id == Constants.NEW_ITEM_ID) {
                insertMealFoodUseCase.insertMealFood(it, mealId)
            }
        }
    }

    private suspend fun updateEditedMealFoods(
        oldMealFoods: List<DbMealFood>,
        updatedMealFoods: List<UiMealFood>
    ) {
        val oldMealFoodIds = oldMealFoods.map { it.id }
        val updatedMealFoodIds = updatedMealFoods.map { it.id }
        val editedMealFoodIds = oldMealFoodIds intersect updatedMealFoodIds

        editedMealFoodIds.forEach { editedMealFoodId ->
            val editedMealFood = updatedMealFoods.find { it.id == editedMealFoodId }
            editedMealFood?.let { updateMealFoodUseCase.updateMealFood(it) }
        }
    }

    private suspend fun deleteOldMealFoodsThatAreNotInUpdatedMealFoods(
        oldMealFoodIds: IntArray,
        updatedMealFoodsIds: IntArray
    ) {
        val mealFoodsToDelete = oldMealFoodIds.toList() subtract updatedMealFoodsIds.toList()
        mealFoodsToDelete.forEach {
            repository.deleteMealFood(it)
        }
    }
}