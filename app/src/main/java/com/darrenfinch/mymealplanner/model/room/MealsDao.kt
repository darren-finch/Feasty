package com.darrenfinch.mymealplanner.model.room

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface MealsDao
{
    @Query("SELECT * FROM meals")
    suspend fun getMeals() : List<DatabaseMeal>

    @Insert
    suspend fun insertMeal(meal: DatabaseMeal)

    @Update
    suspend fun updateMeal(meal: DatabaseMeal)

    @Delete
    suspend fun deleteMeal(meal: DatabaseMeal)
}