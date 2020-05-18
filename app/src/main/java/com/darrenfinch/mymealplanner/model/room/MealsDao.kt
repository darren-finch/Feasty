package com.darrenfinch.mymealplanner.model.room

import androidx.room.*

@Dao
interface MealsDao
{
    @Query("SELECT * FROM meals")
    suspend fun getMeals() : List<DatabaseMeal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: DatabaseMeal)

    @Update
    suspend fun updateMeal(meal: DatabaseMeal)

    @Delete
    suspend fun deleteMeal(meal: DatabaseMeal)
}