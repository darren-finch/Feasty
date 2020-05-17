package com.darrenfinch.mymealplanner.model.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

abstract class MealPlannerDatabase : RoomDatabase()
{
    abstract fun MealsDao() : MealsDao
    abstract fun FoodsDao() : FoodsDao

    fun getInstance(context: Context) : MealPlannerDatabase
    {
        if(INSTANCE == null)
        {
            synchronized(this)
            {
                INSTANCE = Room.databaseBuilder(context, MealPlannerDatabase::class.java, "MealPlannerDatabase")
                    .fallbackToDestructiveMigration()
                    .build()
                return INSTANCE!!
            }
        }
        else
            return INSTANCE!!
    }

    companion object
    {
        private var INSTANCE: MealPlannerDatabase? = null
    }
}