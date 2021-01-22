package com.darrenfinch.mymealplanner.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.darrenfinch.mymealplanner.model.room.daos.*
import com.darrenfinch.mymealplanner.model.room.models.foods.DatabaseFood
import com.darrenfinch.mymealplanner.model.room.models.mealplans.DatabaseMealPlan
import com.darrenfinch.mymealplanner.model.room.models.mealplans.DatabaseMealPlanMeal
import com.darrenfinch.mymealplanner.model.room.models.meals.DatabaseMeal
import com.darrenfinch.mymealplanner.model.room.models.meals.DatabaseMealFood

@Database(
    entities = [DatabaseMeal::class, DatabaseFood::class, DatabaseMealFood::class, DatabaseMealPlan::class, DatabaseMealPlanMeal::class],
    version = 15,
    exportSchema = false
)
@TypeConverters(RoomConverters::class)
abstract class FeastyDatabase : RoomDatabase() {
    abstract fun mealsDao(): MealsDao
    abstract fun foodsDao(): FoodsDao
    abstract fun mealFoodsDao(): MealFoodsDao
    abstract fun mealPlansDao(): MealPlansDao
    abstract fun mealPlanMealsDao(): MealPlanMealsDao
}