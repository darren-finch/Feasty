package com.darrenfinch.mymealplanner

import androidx.lifecycle.MutableLiveData
import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.constants.DefaultModels

object TestData {
    // I re-declared these constants because they are in use already by many different tests
    const val DEFAULT_INVALID_MEAL_FOOD_ID = Constants.DEFAULT_INVALID_MEAL_FOOD_ID
    const val DEFAULT_VALID_MEAL_FOOD_ID = Constants.DEFAULT_VALID_MEAL_FOOD_ID
    const val DEFAULT_MEAL_FOOD_DATA_TITLE = Constants.DEFAULT_MEAL_FOOD_DATA_TITLE

    const val DEFAULT_INVALID_FOOD_ID = Constants.DEFAULT_INVALID_FOOD_ID
    const val DEFAULT_VALID_FOOD_ID = Constants.DEFAULT_VALID_FOOD_ID
    const val DEFAULT_FOOD_DATA_TITLE = Constants.DEFAULT_FOOD_DATA_TITLE

    const val DEFAULT_INVALID_MEAL_ID = Constants.DEFAULT_INVALID_MEAL_ID
    const val DEFAULT_VALID_MEAL_ID = Constants.DEFAULT_VALID_MEAL_ID
    const val DEFAULT_MEAL_DATA_TITLE = Constants.DEFAULT_MEAL_DATA_TITLE

    const val DEFAULT_CALORIES = Constants.DEFAULT_CALORIES
    const val DEFAULT_CARBS = Constants.DEFAULT_CARBS
    const val DEFAULT_FATS = Constants.DEFAULT_FATS
    const val DEFAULT_PROTEINS = Constants.DEFAULT_PROTEINS



    // DEFAULT MACROS
    val defaultMacroNutrients = DefaultModels.defaultMacroNutrients


    // MEAL FOODS
    val defaultMealFood = DefaultModels.defaultMealFood
    val defaultMealFood2 = defaultMealFood.copy(id = DEFAULT_VALID_MEAL_FOOD_ID + 1, foodId = DEFAULT_VALID_FOOD_ID + 1)
    val defaultMealFoodList = listOf(defaultMealFood, defaultMealFood2)
    val defaultMealFoodLiveData = MutableLiveData(defaultMealFood)
    val defaultMealFoodListLiveData = MutableLiveData(defaultMealFoodList)



    // FOODS
    val defaultFood = DefaultModels.defaultFood
    val defaultFood2 = defaultFood.copy(id = DEFAULT_VALID_FOOD_ID + 1)
    val defaultFoodDataList = listOf(defaultFood, defaultFood2)
    val defaultFoodLiveData = MutableLiveData(defaultFood)
    val defaultFoodListLiveData = MutableLiveData(defaultFoodDataList)



    // MEALS
    val defaultMeal = DefaultModels.defaultMeal
    val defaultMeal2 = defaultMeal.copy(id = DEFAULT_VALID_MEAL_ID + 1)
    val defaultMealWithMealFood = defaultMeal.copy(foods = listOf(defaultMealFood))
    val defaultMealListData = listOf(defaultMeal, defaultMeal2)
    val defaultMealLiveData = MutableLiveData(defaultMeal)
    val defaultMealListLiveData = MutableLiveData(defaultMealListData)
}