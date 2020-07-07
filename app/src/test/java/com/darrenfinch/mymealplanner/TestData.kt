package com.darrenfinch.mymealplanner

import com.darrenfinch.mymealplanner.model.data.Food
import com.darrenfinch.mymealplanner.model.data.MacroNutrients
import com.darrenfinch.mymealplanner.model.data.Meal
import com.darrenfinch.mymealplanner.model.data.MetricUnit

object TestData {
    private const val DEFAULT_MEAL_DATA_TITLE = "DEFAULT_MEAL_DATA_TITLE"
    private const val DEFAULT_FOOD_DATA_TITLE = "DEFAULT_FOOD_DATA_TITLE"

    val defaultMealData = Meal(0, DEFAULT_MEAL_DATA_TITLE, listOf())
    val defaultMealData2 = Meal(1, DEFAULT_MEAL_DATA_TITLE, listOf())
    val defaultMealListData = listOf(defaultMealData, defaultMealData2)

    val defaultFoodData = Food(
        0,
        DEFAULT_FOOD_DATA_TITLE, 0.0, MetricUnit.defaultUnit, MacroNutrients(0, 0, 0, 0)
    )
    val defaultFoodData2 = Food(
        1,
        DEFAULT_FOOD_DATA_TITLE, 0.0, MetricUnit.defaultUnit, MacroNutrients(0, 0, 0, 0)
    )
    val defaultFoodDataList = listOf(defaultFoodData, defaultFoodData2)
}