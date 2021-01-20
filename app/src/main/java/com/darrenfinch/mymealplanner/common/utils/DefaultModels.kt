package com.darrenfinch.mymealplanner.common.utils

import com.darrenfinch.mymealplanner.common.misc.Constants.DEFAULT_CALORIES
import com.darrenfinch.mymealplanner.common.misc.Constants.DEFAULT_CARBS
import com.darrenfinch.mymealplanner.common.misc.Constants.DEFAULT_FATS
import com.darrenfinch.mymealplanner.common.misc.Constants.DEFAULT_FOOD_DATA_TITLE
import com.darrenfinch.mymealplanner.common.misc.Constants.DEFAULT_MEAL_DATA_TITLE
import com.darrenfinch.mymealplanner.common.misc.Constants.DEFAULT_MEAL_FOOD_DATA_TITLE
import com.darrenfinch.mymealplanner.common.misc.Constants.DEFAULT_PROTEINS
import com.darrenfinch.mymealplanner.common.misc.Constants.VALID_ID
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.foods.models.Food
import com.darrenfinch.mymealplanner.meals.models.Meal
import com.darrenfinch.mymealplanner.meals.models.MealFood
import com.darrenfinch.mymealplanner.mealplans.models.MealPlan
import com.darrenfinch.mymealplanner.foods.models.MacroNutrients

object DefaultModels {
    val defaultMealPlan = MealPlan(
        id = VALID_ID,
        title = "",
        requiredCalories = 0,
        requiredCarbohydrates = 0,
        requiredFats = 0,
        requiredProteins = 0
    )

    val defaultMacroNutrients = MacroNutrients(
        calories = DEFAULT_CALORIES,
        carbs = DEFAULT_CARBS,
        fats = DEFAULT_FATS,
        proteins = DEFAULT_PROTEINS
    )

    val defaultMealFood = MealFood(
        id = VALID_ID,
        foodId = VALID_ID,
        mealId = VALID_ID,
        title = DEFAULT_MEAL_FOOD_DATA_TITLE,
        desiredServingSize = PhysicalQuantity.defaultPhysicalQuantity,
        macroNutrients = defaultMacroNutrients
    )

    val defaultFood = Food(
        id = VALID_ID,
        title = DEFAULT_FOOD_DATA_TITLE,
        servingSize = PhysicalQuantity.defaultPhysicalQuantity,
        macroNutrients = defaultMacroNutrients
    )

    val defaultMeal = Meal(
        id = VALID_ID,
        title = DEFAULT_MEAL_DATA_TITLE,
        foods = listOf()
    )
}
