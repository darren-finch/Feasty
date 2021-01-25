package com.darrenfinch.mymealplanner.meals.usecases

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.foods.models.domain.Food
import com.darrenfinch.mymealplanner.foods.models.domain.MacroCalculator
import com.darrenfinch.mymealplanner.foods.models.mappers.uiFoodToFood
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood
import com.darrenfinch.mymealplanner.meals.models.domain.MealFood
import com.darrenfinch.mymealplanner.meals.models.mappers.mealToUiMeal
import com.darrenfinch.mymealplanner.meals.models.mappers.uiMealToMeal
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity

class AddMealFoodToMealUseCase {
    fun addMealFoodToMeal(meal: UiMeal, food: UiFood, desiredServingSize: PhysicalQuantity): UiMeal {
        val regularMeal = uiMealToMeal(meal)
        val regularFood = uiFoodToFood(food)
        val newMealFood = foodToMealFood(regularFood, regularMeal.id, desiredServingSize)
        return mealToUiMeal(regularMeal.copy(foods = regularMeal.foods + newMealFood))
    }

    private fun foodToMealFood(
        food: Food,
        mealId: Int,
        desiredServingSize: PhysicalQuantity
    ): MealFood {
        return MealFood(
            // This is set to an invalid id so that we can know which meal foods to insert to the db and which to update.
            Constants.INVALID_ID,
            food.id,
            mealId,
            food.title,
            desiredServingSize,
            MacroCalculator.baseMacrosOnNewServingSize(
                food.macroNutrients,
                food.servingSize,
                desiredServingSize
            )
        )
    }
}