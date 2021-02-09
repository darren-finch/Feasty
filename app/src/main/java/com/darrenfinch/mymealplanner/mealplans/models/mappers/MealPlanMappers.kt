package com.darrenfinch.mymealplanner.mealplans.models.mappers

import com.darrenfinch.mymealplanner.mealplans.models.domain.MealPlan
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlan
import com.darrenfinch.mymealplanner.data.room.models.mealplans.DbMealPlan

fun dbMealPlanToMealPlan(dbMealPlan: DbMealPlan) = MealPlan(
    id = dbMealPlan.id,
    title = dbMealPlan.title,
    requiredCalories = dbMealPlan.requiredCalories,
    requiredCarbohydrates = dbMealPlan.requiredCarbohydrates,
    requiredFats = dbMealPlan.requiredFats,
    requiredProteins = dbMealPlan.requiredProteins
)

fun mealPlanToUiMealPlan(mealPlan: MealPlan) = UiMealPlan(
    id = mealPlan.id,
    title = mealPlan.title,
    requiredCalories = mealPlan.requiredCalories,
    requiredCarbs = mealPlan.requiredCarbohydrates,
    requiredFats = mealPlan.requiredFats,
    requiredProteins = mealPlan.requiredProteins
)

fun uiMealPlanToMealPlan(uiMealPlan: UiMealPlan) = MealPlan(
    id = uiMealPlan.id,
    title = uiMealPlan.title,
    requiredCalories = uiMealPlan.requiredCalories,
    requiredCarbohydrates = uiMealPlan.requiredCarbs,
    requiredFats = uiMealPlan.requiredFats,
    requiredProteins = uiMealPlan.requiredProteins
)

fun mealPlanToDbMealPlan(mealPlan: MealPlan) = DbMealPlan(
    id = mealPlan.id,
    title = mealPlan.title,
    requiredCalories = mealPlan.requiredCalories,
    requiredCarbohydrates = mealPlan.requiredCarbohydrates,
    requiredFats = mealPlan.requiredFats,
    requiredProteins = mealPlan.requiredProteins
)