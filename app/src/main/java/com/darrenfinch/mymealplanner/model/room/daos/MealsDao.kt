package com.darrenfinch.mymealplanner.model.room.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.darrenfinch.mymealplanner.model.room.entities.DatabaseMeal

@Dao
interface MealsDao
{
    @Query("SELECT * FROM meals")
    fun getMeals() : LiveData<List<DatabaseMeal>>

    @Query("SELECT * FROM meals WHERE id = :mealId")
    fun getMeal(mealId: Int): DatabaseMeal

    @Query("SELECT * FROM meals WHERE id = :mealId")
    suspend fun getMealSuspended(mealId: Int): DatabaseMeal

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: DatabaseMeal): Long

    @Update
    suspend fun updateMeal(meal: DatabaseMeal)

    @Query("DELETE FROM meals WHERE id = :mealId")
    suspend fun deleteMeal(mealId: Int)

    @Query("DELETE FROM meals")
    suspend fun deleteAllMeals()
}