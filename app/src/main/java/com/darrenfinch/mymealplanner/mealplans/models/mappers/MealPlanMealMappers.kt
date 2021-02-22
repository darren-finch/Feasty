package com.darrenfinch.mymealplanner.mealplans.models.mappers

import com.darrenfinch.mymealplanner.mealplans.models.domain.MealPlanMeal
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlanMeal
import com.darrenfinch.mymealplanner.meals.models.mappers.dbMealFoodsToMealFoods
import com.darrenfinch.mymealplanner.meals.models.mappers.mealFoodToUiMealFood
import com.darrenfinch.mymealplanner.meals.models.mappers.uiMealFoodToMealFood
import com.darrenfinch.mymealplanner.data.room.models.foods.DbFood
import com.darrenfinch.mymealplanner.data.room.models.mealplans.DbMealPlan
import com.darrenfinch.mymealplanner.data.room.models.mealplans.DbMealPlanMeal
import com.darrenfinch.mymealplanner.data.room.models.meals.DbMeal
import com.darrenfinch.mymealplanner.data.room.models.meals.DbMealFood

fun dbMealPlanMealToMealPlanMeal(
    dbMealPlanMeal: DbMealPlanMeal,
    dbMeal: DbMeal,
    dbMealPlan: DbMealPlan,
    dbMealFoods: List<DbMealFood>,
    dbFoodReferences: List<DbFood?>
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
    mealId = mealPlanMeal.mealId,
    mealPlanId = mealPlanMeal.mealPlanId,
    foods = mealPlanMeal.foods.map {
        mealFoodToUiMealFood(it)
    }
)

fun uiMealPlanMealToMealPlanMeal(uiMealPlanMeal: UiMealPlanMeal) = MealPlanMeal(
    id = uiMealPlanMeal.id,
    title = uiMealPlanMeal.title,
    mealId = uiMealPlanMeal.mealId,
    mealPlanId = uiMealPlanMeal.mealPlanId,
    foods = uiMealPlanMeal.foods.map {
        uiMealFoodToMealFood(it)
    }
)

fun mealPlanMealToDbMealPlanMeal(mealPlanMeal: MealPlanMeal) = DbMealPlanMeal(
    id = mealPlanMeal.id,
    referenceMealId = mealPlanMeal.mealId,
    mealPlanOwnerId = mealPlanMeal.mealPlanId,
)

