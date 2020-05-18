package com.darrenfinch.mymealplanner.utils

import com.darrenfinch.mymealplanner.model.room.DatabaseMeal
import com.darrenfinch.mymealplanner.model.room.Food
import com.darrenfinch.mymealplanner.model.room.MeasurementUnit
import com.darrenfinch.mymealplanner.model.room.Meal

object Utils {
    fun createSampleMeals(): MutableList<Meal> {
        val meals = mutableListOf<Meal>()

        //Breakfast
        val breakfastFoods = mutableListOf<Food>()
        breakfastFoods.add(
            Food(
                0,
                "Eggs",
                3.0,
                MeasurementUnit.UNIT,
                180,
                0,
                18,
                18
            )
        )
        breakfastFoods.add(
            Food(
                1,
                "Toast",
                2.0,
                MeasurementUnit.UNIT,
                120,
                28,
                8,
                2
            )
        )
        meals.add(
            Meal(
                "Breakfast",
                breakfastFoods
            )
        )

        val lunchFoods = mutableListOf<Food>()
        lunchFoods.add(
            Food(
                2,
                "Chicken",
                4.0,
                MeasurementUnit.OUNCE,
                170,
                0,
                26,
                8
            )
        )
        lunchFoods.add(
            Food(
                3,
                "Rice",
                150.0,
                MeasurementUnit.GRAM,
                170,
                38,
                8,
                3
            )
        )
        meals.add(
            Meal(
                "Lunch",
                lunchFoods
            )
        )

        val dinnerFoods = mutableListOf<Food>()
        dinnerFoods.add(
            Food(
                4,
                "Steak",
                4.0,
                MeasurementUnit.OUNCE,
                206,
                0,
                28,
                14
            )
        )
        dinnerFoods.add(
            Food(
                5,
                "Sweet Potato",
                150.0,
                MeasurementUnit.GRAM,
                135,
                30,
                5,
                2
            )
        )
        meals.add(
            Meal(
                "Dinner",
                dinnerFoods
            )
        )

        return meals
    }

    fun createSampleDatabaseMeals(): MutableList<DatabaseMeal> {
        val meals = mutableListOf<DatabaseMeal>()
        meals.add(
            DatabaseMeal(
                0,
                "Breakfast",
                mutableListOf()
            )
        )
        meals.add(
            DatabaseMeal(
                1,
                "Breakfast",
                mutableListOf()
            )
        )
        meals.add(
            DatabaseMeal(
                2,
                "Breakfast",
                mutableListOf()
            )
        )
        return meals
    }

    fun createSampleFoods(): MutableList<Food> {
        val foodsList = mutableListOf<Food>().apply {
            add(
                Food(
                    0,
                    "Eggs",
                    3.0,
                    MeasurementUnit.UNIT,
                    180,
                    0,
                    18,
                    18
                )
            )
            add(
                Food(
                    1,
                    "Toast",
                    2.0,
                    MeasurementUnit.UNIT,
                    120,
                    28,
                    8,
                    2
                )
            )
            add(
                Food(
                    2,
                    "Rice",
                    150.0,
                    MeasurementUnit.GRAM,
                    170,
                    38,
                    8,
                    3
                )
            )
            add(
                Food(
                    3,
                    "Chicken",
                    4.0,
                    MeasurementUnit.OUNCE,
                    170,
                    0,
                    26,
                    8
                )
            )
            add(
                Food(
                    4,
                    "Steak",
                    4.0,
                    MeasurementUnit.OUNCE,
                    206,
                    0,
                    28,
                    14
                )
            )
            add(
                Food(
                    5,
                    "Sweet Potato",
                    150.0,
                    MeasurementUnit.GRAM,
                    135,
                    30,
                    5,
                    2
                )
            )
        }
        return foodsList
    }
}