package com.darrenfinch.mymealplanner.screens.mealform

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.extensions.indexIsValid
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood
import java.io.Serializable

class MealFormData : Serializable {
    private var id = Constants.NEW_ITEM_ID
    private var title = ""
    private var mealFoods = mutableListOf<UiMealFood>()

    fun bindMealDetails(meal: UiMeal) {
        id = meal.id
        title = meal.title
        mealFoods = meal.foods.toMutableList()
    }

    fun getMealDetails() = UiMeal(
        id = id,
        title = title,
        foods = mealFoods
    )

    fun setTitle(newTitle: String) {
        title = newTitle
    }

    fun addMealFood(selectedFood: UiFood) {
        val mealFood = UiMealFood(
            id = Constants.NEW_ITEM_ID,
            title = selectedFood.title,
            foodId = selectedFood.id,
            mealId = id,
            desiredServingSize = selectedFood.servingSize,
            originalServingSize = selectedFood.servingSize,
            originalMacroNutrients = selectedFood.macroNutrients
        )
        mealFoods.add(mealFood)
    }

    fun updateMealFood(editedMealFood: UiMealFood, index: Int) {
        if (mealFoods.indexIsValid(index)) {
            mealFoods[index] = editedMealFood
        }
    }

    fun removeMealFood(index: Int) {
        if (mealFoods.indexIsValid(index)) {
            mealFoods.removeAt(index)
        }
    }
}