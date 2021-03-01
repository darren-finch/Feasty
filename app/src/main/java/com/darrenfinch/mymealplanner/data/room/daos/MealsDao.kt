package com.darrenfinch.mymealplanner.data.room.daos

import androidx.room.*
import com.darrenfinch.mymealplanner.data.room.models.meals.DbMeal

@Dao
interface MealsDao {
    @Query("SELECT * FROM meals")
    suspend fun getAllMeals(): List<DbMeal>

    @Query("SELECT * FROM meals WHERE title LIKE :query")
    suspend fun getMealsForQuery(query: String): List<DbMeal>

    @Query("SELECT * FROM meals WHERE id = :id")
    suspend fun getMeal(id: Int): DbMeal?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: DbMeal): Long

    @Update
    suspend fun updateMeal(meal: DbMeal)

    @Query("DELETE FROM meals WHERE id = :id")
    suspend fun deleteMeal(id: Int)
    @Query("DELETE FROM meals")
    suspend fun deleteAllMeals()
}