package com.darrenfinch.mymealplanner.screens.mealform

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.ui.viewmodels.StatefulVm
import com.darrenfinch.mymealplanner.common.ui.viewmodels.StatefulVmListProperty
import com.darrenfinch.mymealplanner.common.ui.viewmodels.StatefulVmProperty
import com.darrenfinch.mymealplanner.foods.models.domain.MacroCalculator
import com.darrenfinch.mymealplanner.foods.models.mappers.macroNutrientsToUiMacroNutrients
import com.darrenfinch.mymealplanner.foods.models.mappers.uiMacroNutrientsToMacroNutrients
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity

class MealFormVm : StatefulVm() {
    private var id = Constants.VALID_ID
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

    fun addMealFood(selectedFood: UiFood, selectedFoodQuantity: PhysicalQuantity) {
        mealFoods.set((mealFoods.get() + foodToMealFood(selectedFood, id, selectedFoodQuantity)).toMutableList())
    }

    private fun foodToMealFood(
        food: UiFood,
        mealId: Int,
        desiredServingSize: PhysicalQuantity
    ): UiMealFood {
        return UiMealFood(
            // New meal foods have invalid ids so that we can know which meal foods to insert to the db and which to update.
            Constants.INVALID_ID,
            food.id,
            mealId,
            food.title,
            desiredServingSize,
            macroNutrientsToUiMacroNutrients(
                MacroCalculator.baseMacrosOnNewServingSize(
                    uiMacroNutrientsToMacroNutrients(food.macroNutrients),
                    food.servingSize,
                    desiredServingSize
                )
            )
        )
    }
}