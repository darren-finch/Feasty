package com.darrenfinch.mymealplanner.screens.mealform

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.extensions.indexIsValid
import com.darrenfinch.mymealplanner.common.ui.viewmodels.StatefulVm
import com.darrenfinch.mymealplanner.common.ui.viewmodels.StatefulVmListProperty
import com.darrenfinch.mymealplanner.common.ui.viewmodels.StatefulVmProperty
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood

class MealFormVm : StatefulVm() {
    private var id = Constants.NEW_ITEM_ID
    private var title = StatefulVmProperty("", this)
    private var mealFoods = StatefulVmListProperty<UiMealFood>(this)

    fun bindMealDetails(meal: UiMeal) {
        id = meal.id
        title.setWithoutNotifying(meal.title)
        mealFoods.set(meal.foods)
    }

    fun getMealDetails() = UiMeal(
        id = id,
        title = title.get(),
        foods = mealFoods.get()
    )

    fun setTitle(newTitle: String) {
        title.set(newTitle)
    }

    fun addMealFood(selectedFood: UiFood) {
        val mealFoodsList = mealFoods.get()
        val mealFood = UiMealFood(
            id = Constants.NEW_ITEM_ID,
            title = selectedFood.title,
            foodId = selectedFood.id,
            mealId = id,
            desiredServingSize = selectedFood.servingSize,
            originalServingSize = selectedFood.servingSize,
            macroNutrients = selectedFood.macroNutrients
        )
        mealFoods.set(mealFoodsList + mealFood)
    }

    fun updateMealFood(editedMealFood: UiMealFood, index: Int) {
        val mealFoodsList = mealFoods.get()

        if (mealFoodsList.indexIsValid(index)) {
            mealFoodsList[index] = editedMealFood
            mealFoods.set(mealFoodsList)
        }
    }

    fun removeMealFood(index: Int) {
        val mealFoodsList = mealFoods.get()

        if (mealFoodsList.indexIsValid(index)) {
            mealFoodsList.removeAt(index)
            mealFoods.set(mealFoodsList)
        }
    }
}