package com.darrenfinch.mymealplanner

import androidx.lifecycle.MutableLiveData
import com.darrenfinch.mymealplanner.model.data.*

object TestData {
    private const val DEFAULT_MEAL_DATA_TITLE = ""
    private const val DEFAULT_FOOD_DATA_TITLE = ""

    val defaultMealFood = MealFood(
        0,
        DEFAULT_FOOD_DATA_TITLE, 0.0, 0.0, MetricUnit.defaultUnit, MacroNutrients(0, 0, 0, 0)
    )
    val defaultMealFoodLiveData = MutableLiveData(defaultMealFood)

    val defaultMeal = Meal(0, DEFAULT_MEAL_DATA_TITLE, listOf())
    val defaultMeal2 = Meal(1, DEFAULT_MEAL_DATA_TITLE, listOf())
    val defaultMealWithMealFood = Meal(0, DEFAULT_MEAL_DATA_TITLE, listOf(defaultMealFood))
    val defaultMealListData = listOf(defaultMeal, defaultMeal2)
    val defaultMealLiveData = MutableLiveData(defaultMeal)
    val defaultMealListLiveData = MutableLiveData(defaultMealListData)

    val defaultFood = Food(
        0,
        DEFAULT_FOOD_DATA_TITLE, 0.0, MetricUnit.defaultUnit, MacroNutrients(0, 0, 0, 0)
    )
    val defaultFood2 = Food(
        1,
        DEFAULT_FOOD_DATA_TITLE, 0.0, MetricUnit.defaultUnit, MacroNutrients(0, 0, 0, 0)
    )
    val defaultFoodDataList = listOf(defaultFood, defaultFood2)
    val defaultFoodLiveData = MutableLiveData(defaultFood)
    val defaultFoodListLiveData = MutableLiveData(defaultFoodDataList)
}