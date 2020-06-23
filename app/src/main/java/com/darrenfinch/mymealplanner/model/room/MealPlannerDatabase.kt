package com.darrenfinch.mymealplanner.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.darrenfinch.mymealplanner.model.data.DatabaseMeal
import com.darrenfinch.mymealplanner.model.data.Food

@Database(entities = [DatabaseMeal::class, Food::class], version = 4, exportSchema = false)
@TypeConverters(RoomConverters::class)
abstract class MealPlannerDatabase : RoomDatabase()
{
    abstract fun mealsDao() : MealsDao
    abstract fun foodsDao() : FoodsDao
}