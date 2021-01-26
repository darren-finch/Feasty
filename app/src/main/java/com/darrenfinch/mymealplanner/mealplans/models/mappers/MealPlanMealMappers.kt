package com.darrenfinch.mymealplanner.mealplans.models.mappers

import com.darrenfinch.mymealplanner.mealplans.models.domain.MealPlanMeal
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlanMeal
import com.darrenfinch.mymealplanner.meals.models.mappers.dbMealFoodsToMealFoods
import com.darrenfinch.mymealplanner.meals.models.mappers.mealFoodToUiMealFood
import com.darrenfinch.mymealplanner.meals.models.mappers.uiMealFoodToMealFood
import com.darrenfinch.mymealplanner.model.room.models.foods.DatabaseFood
import com.darrenfinch.mymealplanner.model.room.models.mealplans.DatabaseMealPlan
import com.darrenfinch.mymealplanner.model.room.models.mealplans.DatabaseMealPlanMeal
import com.darrenfinch.mymealplanner.model.room.models.meals.DatabaseMeal
import com.darrenfinch.mymealplanner.model.room.models.meals.DatabaseMealFood

fun dbMealPlanMealToMealPlanMeal(
    dbMealPlanMeal: DatabaseMealPlanMeal,
    dbMeal: DatabaseMeal,
    dbMealPlan: DatabaseMealPlan,
    dbMealFoods: List<DatabaseMealFood>,
    dbFoodReferences: List<DatabaseFood?>
) = MealPlanMeal(
    id = dbMealPlanMeal.id,
    title = dbMeal.title,
    mealId = dbMeal.id,
    mealPlanId = dbMealPlan.id,
    foods = dbMealFoodsToMealFoods(dbMealFoods, dbFoodReferences)
)

fun mealPlanMealToUiMealPlanMeal(mealPlanMeal: MealPlanMeal) = UiMealPlanMeal(
    id = mealPlanMeal.id,
    title = mealPlanMeal.title,
    mealId = mealPlanMeal.id,
    mealPlanId = mealPlanMeal.id,
    foods = mealPlanMeal.foods.map {
        mealFoodToUiMealFood(it)
    }
)

fun uiMealPlanMealToMealPlanMeal(uiMealPlanMeal: UiMealPlanMeal) = MealPlanMeal(
    id = uiMealPlanMeal.id,
    title = uiMealPlanMeal.title,
    mealId = uiMealPlanMeal.id,
    mealPlanId = uiMealPlanMeal.id,
    foods = uiMealPlanMeal.foods.map {
        uiMealFoodToMealFood(it)
    }
)

fun mealPlanMealToDbMealPlanMeal(mealPlanMeal: MealPlanMeal) = DatabaseMealPlanMeal(
    id = mealPlanMeal.id,
    mealId = mealPlanMeal.id,
    mealPlanId = mealPlanMeal.id,
)

