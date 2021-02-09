package com.darrenfinch.mymealplanner.meals.models.mappers

import com.darrenfinch.mymealplanner.meals.models.domain.Meal
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import com.darrenfinch.mymealplanner.data.room.models.foods.DbFood
import com.darrenfinch.mymealplanner.data.room.models.meals.DbMeal
import com.darrenfinch.mymealplanner.data.room.models.meals.DbMealFood

fun dbMealToMeal(
    dbMeal: DbMeal,
    dbMealFoods: List<DbMealFood>,
    dbFoodReferences: List<DbFood?>
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

fun mealToDbMeal(meal: Meal) = DbMeal(
    id = meal.id,
    title = meal.title
)