package com.darrenfinch.mymealplanner.utils

import com.darrenfinch.mymealplanner.data.FoodModel
import com.darrenfinch.mymealplanner.data.MeasurementUnit
import com.darrenfinch.mymealplanner.data.MealModel

object Utils
{
    fun createSampleMeals() : MutableList<MealModel>
    {
        val meals = mutableListOf<MealModel>()

        //Breakfast
        val breakfastFoods = mutableListOf<FoodModel>()
        breakfastFoods.add(FoodModel("Eggs", 3.0, MeasurementUnit.UNIT,180, 0, 18, 18))
        breakfastFoods.add(FoodModel("Toast", 2.0, MeasurementUnit.UNIT, 120, 28, 8, 2))
        meals.add(MealModel("Breakfast", breakfastFoods))

        val lunchFoods = mutableListOf<FoodModel>()
        lunchFoods.add(FoodModel("Chicken", 4.0, MeasurementUnit.OUNCE,170, 0, 26, 8))
        lunchFoods.add(FoodModel("Rice", 150.0, MeasurementUnit.GRAM,170, 38, 8, 3))
        meals.add(MealModel("Lunch", lunchFoods))

        val dinnerFoods = mutableListOf<FoodModel>()
        dinnerFoods.add(FoodModel("Steak", 4.0, MeasurementUnit.OUNCE,206, 0, 28, 14))
        dinnerFoods.add(FoodModel("Sweet Potato", 150.0, MeasurementUnit.GRAM,135, 30, 5, 2))
        meals.add(MealModel("Dinner", dinnerFoods))

        return meals
    }

    fun createSampleFoods() : MutableList<FoodModel>
    {
        var foodsList = mutableListOf<FoodModel>()
        foodsList.add(FoodModel("Eggs", 3.0, MeasurementUnit.UNIT,180, 0, 18, 18))
        foodsList.add(FoodModel("Toast", 2.0, MeasurementUnit.UNIT, 120, 28, 8, 2))
        foodsList.add(FoodModel("Chicken", 4.0, MeasurementUnit.OUNCE,170, 0, 26, 8))
        foodsList.add(FoodModel("Rice", 150.0, MeasurementUnit.GRAM,170, 38, 8, 3))
        foodsList.add(FoodModel("Steak", 4.0, MeasurementUnit.OUNCE,206, 0, 28, 14))
        foodsList.add(FoodModel("Sweet Potato", 150.0, MeasurementUnit.GRAM,135, 30, 5, 2))
        return foodsList
    }
}