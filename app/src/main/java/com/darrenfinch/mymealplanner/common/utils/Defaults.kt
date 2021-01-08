package com.darrenfinch.mymealplanner.common.utils

import com.darrenfinch.mymealplanner.common.misc.Constants.DEFAULT_CALORIES
import com.darrenfinch.mymealplanner.common.misc.Constants.DEFAULT_CARBS
import com.darrenfinch.mymealplanner.common.misc.Constants.DEFAULT_FATS
import com.darrenfinch.mymealplanner.common.misc.Constants.DEFAULT_FOOD_DATA_TITLE
import com.darrenfinch.mymealplanner.common.misc.Constants.DEFAULT_MEAL_DATA_TITLE
import com.darrenfinch.mymealplanner.common.misc.Constants.DEFAULT_MEAL_FOOD_DATA_TITLE
import com.darrenfinch.mymealplanner.common.misc.Constants.DEFAULT_PROTEINS
import com.darrenfinch.mymealplanner.common.misc.Constants.DEFAULT_VALID_FOOD_ID
import com.darrenfinch.mymealplanner.common.misc.Constants.DEFAULT_VALID_MEAL_FOOD_ID
import com.darrenfinch.mymealplanner.common.misc.Constants.DEFAULT_VALID_MEAL_ID
import com.darrenfinch.mymealplanner.domain.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.model.data.entities.Food
import com.darrenfinch.mymealplanner.model.data.entities.Meal
import com.darrenfinch.mymealplanner.model.data.entities.MealFood
import com.darrenfinch.mymealplanner.model.data.entitysubdata.MacroNutrients

object Defaults {
    val defaultMacroNutrients = MacroNutrients(
        calories = DEFAULT_CALORIES,
        carbs = DEFAULT_CARBS,
        fats = DEFAULT_FATS,
        proteins = DEFAULT_PROTEINS
    )

    val defaultMealFood =MealFood(
        id = DEFAULT_VALID_MEAL_FOOD_ID,
        foodId = DEFAULT_VALID_FOOD_ID,
        mealId = DEFAULT_VALID_MEAL_ID,
        title = DEFAULT_MEAL_FOOD_DATA_TITLE,
        desiredServingSize = PhysicalQuantity.defaultPhysicalQuantity,
        macroNutrients = defaultMacroNutrients
    )

    val defaultFood = Food(
        id = DEFAULT_VALID_FOOD_ID,
        title = DEFAULT_FOOD_DATA_TITLE,
        servingSize = PhysicalQuantity.defaultPhysicalQuantity,
        macroNutrients = defaultMacroNutrients
    )

    val defaultMeal = Meal(
        id = DEFAULT_VALID_MEAL_ID,
        title = DEFAULT_MEAL_DATA_TITLE,
        foods = listOf()
    )
}
