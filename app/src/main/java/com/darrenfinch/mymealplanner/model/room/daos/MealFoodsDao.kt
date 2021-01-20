package com.darrenfinch.mymealplanner.model.room.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.darrenfinch.mymealplanner.model.room.models.DatabaseMealFood

@Dao
interface MealFoodsDao {
    @Query("SELECT * FROM mealFoods WHERE mealId = :id")
    suspend fun getMealFoodsFromMealId(id: Int): List<DatabaseMealFood>

    @Query("SELECT * FROM mealFoods WHERE foodId = :id")
    fun getMealFoodsFromFoodId(id: Int): LiveData<List<DatabaseMealFood>>

    // TODO: REMOVE ASAP when converting to Kotlin Flow
    @Query("SELECT * FROM mealFoods WHERE foodId = :id")
    suspend fun getMealFoodsFromFoodIdSuspended(id: Int): List<DatabaseMealFood>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealFood(mealFood: DatabaseMealFood)

    @Update
    suspend fun updateMealFood(mealFood: DatabaseMealFood)

    @Query("DELETE FROM mealFoods WHERE id = :id")
    suspend fun deleteMealFood(id: Int)
}