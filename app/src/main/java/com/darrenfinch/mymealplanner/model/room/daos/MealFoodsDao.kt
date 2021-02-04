package com.darrenfinch.mymealplanner.model.room.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.darrenfinch.mymealplanner.model.room.models.meals.DatabaseMealFood

@Dao
interface MealFoodsDao {
    @Query("SELECT * FROM mealFoods WHERE mealId = :mealId")
    suspend fun getMealFoodsForMeal(mealId: Int): List<DatabaseMealFood>

    @Query("SELECT * FROM mealFoods WHERE foodId = :foodId")
    suspend fun getMealFoodsForFoodId(foodId: Int): List<DatabaseMealFood>

    @Query("SELECT * FROM mealFoods WHERE id = :id")
    suspend fun getMealFood(id: Int): DatabaseMealFood?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealFood(mealFood: DatabaseMealFood)

    @Update
    suspend fun updateMealFood(mealFood: DatabaseMealFood)

    @Query("DELETE FROM mealFoods WHERE id = :id")
    suspend fun deleteMealFood(id: Int)
}