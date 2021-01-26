package com.darrenfinch.mymealplanner.meals.models.mappers

import com.darrenfinch.mymealplanner.meals.models.domain.Meal
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import com.darrenfinch.mymealplanner.model.room.models.foods.DatabaseFood
import com.darrenfinch.mymealplanner.model.room.models.meals.DatabaseMeal
import com.darrenfinch.mymealplanner.model.room.models.meals.DatabaseMealFood

fun dbMealToMeal(
    dbMeal: DatabaseMeal,
    dbMealFoods: List<DatabaseMealFood>,
    dbFoodReferences: List<DatabaseFood?>
) = Meal(
    id = dbMeal.id,
    title = dbMeal.title,
    foods = dbMealFoodsToMealFoods(dbMealFoods, dbFoodReferences)
)

fun mealToUiMeal(meal: Meal) = UiMeal(
    id = meal.id,
    title = meal.title,
    foods = meal.foods.map {
        mealFoodToUiMealFood(it)
    }
)

fun uiMealToMeal(uiMeal: UiMeal) = Meal(
    id = uiMeal.id,
    title = uiMeal.title,
    foods = uiMeal.foods.map {
        uiMealFoodToMealFood(it)
    }
)

fun mealToDbMeal(meal: Meal) = DatabaseMeal(
    id = meal.id,
    title = meal.title
)