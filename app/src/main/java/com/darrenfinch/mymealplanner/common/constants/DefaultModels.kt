package com.darrenfinch.mymealplanner.common.constants

import com.darrenfinch.mymealplanner.common.constants.Constants.DEFAULT_CALORIES
import com.darrenfinch.mymealplanner.common.constants.Constants.DEFAULT_CARBS
import com.darrenfinch.mymealplanner.common.constants.Constants.DEFAULT_FATS
import com.darrenfinch.mymealplanner.common.constants.Constants.DEFAULT_FOOD_DATA_TITLE
import com.darrenfinch.mymealplanner.common.constants.Constants.DEFAULT_MEAL_DATA_TITLE
import com.darrenfinch.mymealplanner.common.constants.Constants.DEFAULT_MEAL_FOOD_DATA_TITLE
import com.darrenfinch.mymealplanner.common.constants.Constants.DEFAULT_PROTEINS
import com.darrenfinch.mymealplanner.common.constants.Constants.INVALID_ID
import com.darrenfinch.mymealplanner.common.constants.Constants.VALID_ID
import com.darrenfinch.mymealplanner.foods.models.domain.Food
import com.darrenfinch.mymealplanner.foods.models.domain.MacroNutrients
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood
import com.darrenfinch.mymealplanner.foods.models.presentation.UiMacroNutrients
import com.darrenfinch.mymealplanner.mealplans.models.domain.MealPlan
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlan
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlanMeal
import com.darrenfinch.mymealplanner.meals.models.domain.Meal
import com.darrenfinch.mymealplanner.meals.models.domain.MealFood
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity

object DefaultModels {
    // Domain models

    val defaultPhysicalQuantity = PhysicalQuantity.defaultPhysicalQuantity

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



    // Presentation models

    val defaultUiMacroNutrients = UiMacroNutrients(
        calories = DEFAULT_CALORIES,
        carbs = DEFAULT_CARBS,
        fats = DEFAULT_FATS,
        proteins = DEFAULT_PROTEINS
    )
    val defaultUiFood = UiFood(
        id = VALID_ID,
        title = DEFAULT_FOOD_DATA_TITLE,
        servingSize = PhysicalQuantity.defaultPhysicalQuantity,
        macroNutrients = defaultUiMacroNutrients
    )
    val defaultUiMeal = UiMeal(
        id = VALID_ID,
        title = DEFAULT_MEAL_DATA_TITLE,
        foods = listOf()
    )
    val defaultUiMealFood = UiMealFood(
        id = VALID_ID,
        foodId = VALID_ID,
        mealId = VALID_ID,
        title = DEFAULT_MEAL_FOOD_DATA_TITLE,
        desiredServingSize = PhysicalQuantity.defaultPhysicalQuantity,
        macroNutrients = defaultUiMacroNutrients
    )
    val defaultUiMealPlan = UiMealPlan(
        id = VALID_ID,
        title = "",
        requiredCalories = 0,
        requiredCarbs = 0,
        requiredFats = 0,
        requiredProteins = 0
    )
    val defaultUiMealPlanMeal = UiMealPlanMeal(
        id = VALID_ID,
        title = "",
        foods = listOf(),
        mealId = INVALID_ID,
        mealPlanId = INVALID_ID
    )
}
