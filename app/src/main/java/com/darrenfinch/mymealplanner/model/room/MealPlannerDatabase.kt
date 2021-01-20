package com.darrenfinch.mymealplanner.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.darrenfinch.mymealplanner.model.room.daos.*
import com.darrenfinch.mymealplanner.model.room.models.*

@Database(
    entities = [DatabaseMeal::class, DatabaseFood::class, DatabaseMealFood::class, DatabaseMealPlan::class, DatabaseMealPlanMeal::class],
    version = 14,
    exportSchema = false
)
@TypeConverters(RoomConverters::class)
abstract class MealPlannerDatabase : RoomDatabase() {
    abstract fun mealsDao(): MealsDao
    abstract fun foodsDao(): FoodsDao
    abstract fun mealFoodsDao(): MealFoodsDao
    abstract fun mealPlansDao(): MealPlansDao
    abstract fun mealPlanMealsDao(): MealPlanMealsDao
}