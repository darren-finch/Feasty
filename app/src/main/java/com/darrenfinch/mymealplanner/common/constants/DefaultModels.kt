package com.darrenfinch.mymealplanner.common.constants

import com.darrenfinch.mymealplanner.common.constants.Constants.DEFAULT_CALORIES
import com.darrenfinch.mymealplanner.common.constants.Constants.DEFAULT_CARBS
import com.darrenfinch.mymealplanner.common.constants.Constants.DEFAULT_FATS
import com.darrenfinch.mymealplanner.common.constants.Constants.DEFAULT_FOOD_DATA_TITLE
import com.darrenfinch.mymealplanner.common.constants.Constants.DEFAULT_MEAL_DATA_TITLE
import com.darrenfinch.mymealplanner.common.constants.Constants.DEFAULT_MEAL_FOOD_DATA_TITLE
import com.darrenfinch.mymealplanner.common.constants.Constants.DEFAULT_PROTEINS
import com.darrenfinch.mymealplanner.common.constants.Constants.INVALID_ID
import com.darrenfinch.mymealplanner.common.constants.Constants.NEW_ITEM_ID
import com.darrenfinch.mymealplanner.data.room.models.foods.DbFood
import com.darrenfinch.mymealplanner.data.room.models.mealplans.DbMealPlan
import com.darrenfinch.mymealplanner.data.room.models.mealplans.DbMealPlanMeal
import com.darrenfinch.mymealplanner.data.room.models.meals.DbMeal
import com.darrenfinch.mymealplanner.data.room.models.meals.DbMealFood
import com.darrenfinch.mymealplanner.foods.models.domain.Food
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood
import com.darrenfinch.mymealplanner.foods.models.domain.MacroNutrients
import com.darrenfinch.mymealplanner.mealplans.models.domain.MealPlan
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlan
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlanMeal
import com.darrenfinch.mymealplanner.meals.models.domain.Meal
import com.darrenfinch.mymealplanner.meals.models.domain.MealFood
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.screens.mealplan.MealPlanMacros

object DefaultModels {
    // Data models
    val defaultMacros = MacroNutrients(
        calories = DEFAULT_CALORIES,
        carbs = DEFAULT_CARBS,
        fats = DEFAULT_FATS,
        proteins = DEFAULT_PROTEINS
    )
    val defaultDbFood = DbFood(
        id = NEW_ITEM_ID,
        title = "",
        servingSize = PhysicalQuantity.defaultPhysicalQuantity,
        macros = defaultMacros
    )
    val defaultDbMealFood = DbMealFood(
        id = NEW_ITEM_ID,
        referencedFoodId = INVALID_ID,
        mealOwnerId = INVALID_ID,
        desiredServingSize = PhysicalQuantity.defaultPhysicalQuantity
    )
    val defaultDbMeal = DbMeal(
        id = NEW_ITEM_ID,
        title = ""
    )
    val defaultDbMealPlan = DbMealPlan(
        id = NEW_ITEM_ID,
        title = "",
        requiredCalories = 0,
        requiredCarbohydrates = 0,
        requiredFats = 0,
        requiredProteins = 0,
    )
    val defaultDbMealPlanMeal = DbMealPlanMeal(
        id = NEW_ITEM_ID,
        mealPlanOwnerId = INVALID_ID,
        referenceMealId = INVALID_ID
    )


    // Domain models

    val defaultPhysicalQuantity = PhysicalQuantity.defaultPhysicalQuantity

    val defaultMealPlanMacros = MealPlanMacros(
        totalCalories = 0,
        totalCarbs = 0,
        totalFats = 0,
        totalProteins = 0,
        requiredCalories = 0,
        requiredCarbs = 0,
        requiredFats = 0,
        requiredProteins = 0
    )
    val defaultMealPlan = MealPlan(
        id = NEW_ITEM_ID,
        title = "",
        requiredCalories = 0,
        requiredCarbohydrates = 0,
        requiredFats = 0,
        requiredProteins = 0
    )
    val defaultMealFood = MealFood(
        id = NEW_ITEM_ID,
        foodId = INVALID_ID,
        mealId = INVALID_ID,
        title = DEFAULT_MEAL_FOOD_DATA_TITLE,
        desiredServingSize = PhysicalQuantity.defaultPhysicalQuantity,
        originalServingSize = PhysicalQuantity.defaultPhysicalQuantity,
        originalMacros = defaultMacros
    )
    val defaultFood = Food(
        id = NEW_ITEM_ID,
        title = DEFAULT_FOOD_DATA_TITLE,
        servingSize = PhysicalQuantity.defaultPhysicalQuantity,
        macros = defaultMacros
    )
    val defaultMeal = Meal(
        id = NEW_ITEM_ID,
        title = DEFAULT_MEAL_DATA_TITLE,
        foods = listOf()
    )


    // Presentation models

    val defaultUiFood = UiFood(
        id = NEW_ITEM_ID,
        title = DEFAULT_FOOD_DATA_TITLE,
        servingSize = PhysicalQuantity.defaultPhysicalQuantity,
        macros = defaultMacros
    )
    val defaultUiMeal = UiMeal(
        id = NEW_ITEM_ID,
        title = DEFAULT_MEAL_DATA_TITLE,
        foods = listOf()
    )
    val defaultUiMealFood = UiMealFood(
        id = NEW_ITEM_ID,
        foodId = INVALID_ID,
        mealId = INVALID_ID,
        title = DEFAULT_MEAL_FOOD_DATA_TITLE,
        desiredServingSize = PhysicalQuantity.defaultPhysicalQuantity,
        originalServingSize = PhysicalQuantity.defaultPhysicalQuantity,
        originalMacros = defaultMacros,
    )
    val defaultUiMealPlan = UiMealPlan(
        id = NEW_ITEM_ID,
        title = "",
        requiredCalories = 0,
        requiredCarbs = 0,
        requiredFats = 0,
        requiredProteins = 0
    )
    val defaultUiMealPlanMeal = UiMealPlanMeal(
        id = NEW_ITEM_ID,
        title = "",
        foods = listOf(),
        mealId = INVALID_ID,
        mealPlanId = INVALID_ID
    )
}
