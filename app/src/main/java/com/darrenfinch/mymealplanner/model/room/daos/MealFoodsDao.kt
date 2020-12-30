package com.darrenfinch.mymealplanner.model.room.daos

import androidx.room.*
import com.darrenfinch.mymealplanner.model.room.entities.DatabaseMealFood

@Dao
interface MealFoodsDao {
    @Query("SELECT * FROM mealFoods WHERE mealId = :mealId")
    suspend fun getMealFoodsForMeal(mealId: Int): List<DatabaseMealFood>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealFood(mealFood: DatabaseMealFood)

    @Update
    suspend fun updateMealFood(mealFood: DatabaseMealFood)

    @Delete
    suspend fun deleteMealFood(mealFood: DatabaseMealFood)
}