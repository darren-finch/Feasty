package com.darrenfinch.mymealplanner.model.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.darrenfinch.mymealplanner.model.data.DatabaseMeal
import com.darrenfinch.mymealplanner.model.data.Food

@Dao
interface MealsDao
{
    @Query("SELECT * FROM meals")
    fun getMeals() : LiveData<List<DatabaseMeal>>

    @Query("SELECT * FROM meals WHERE id = :mealId")
    fun getMeal(mealId: Int): LiveData<DatabaseMeal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: DatabaseMeal)

    @Update
    suspend fun updateMeal(meal: DatabaseMeal)

    @Delete
    suspend fun deleteMeal(meal: DatabaseMeal)

    @Query("DELETE FROM meals")
    suspend fun deleteAllMeals()
}