package com.darrenfinch.mymealplanner.model.room.daos

import androidx.room.*
import com.darrenfinch.mymealplanner.model.room.models.meals.DatabaseMeal

@Dao
interface MealsDao {
    @Query("SELECT * FROM meals")
    suspend fun getAllMeals(): List<DatabaseMeal>

    @Query("SELECT * FROM meals WHERE id = :id")
    suspend fun getMeal(id: Int): DatabaseMeal?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: DatabaseMeal): Long

    @Update
    suspend fun updateMeal(meal: DatabaseMeal)

    @Query("DELETE FROM meals WHERE id = :id")
    suspend fun deleteMeal(id: Int)

    @Query("DELETE FROM meals")
    suspend fun deleteAllMeals()
}