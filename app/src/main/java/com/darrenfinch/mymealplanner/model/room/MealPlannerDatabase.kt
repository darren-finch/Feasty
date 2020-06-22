package com.darrenfinch.mymealplanner.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DatabaseMeal::class, Food::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MealPlannerDatabase : RoomDatabase()
{
    abstract fun mealsDao() : MealsDao
    abstract fun foodsDao() : FoodsDao
}