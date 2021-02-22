package com.darrenfinch.mymealplanner.data.room.daos

import androidx.room.*
import com.darrenfinch.mymealplanner.data.room.models.meals.DbMealFood

@Dao
interface MealFoodsDao {
    @Query("SELECT * FROM mealFoods WHERE mealOwnerId = :mealId")
    suspend fun getMealFoodsForMeal(mealId: Int): List<DbMealFood>

    @Query("SELECT * FROM mealFoods WHERE referencedFoodId = :foodId")
    suspend fun getMealFoodsForFoodId(foodId: Int): List<DbMealFood>

    @Query("SELECT * FROM mealFoods WHERE id = :id")
    suspend fun getMealFood(id: Int): DbMealFood?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealFood(mealFood: DbMealFood)

    @Update
    suspend fun updateMealFood(mealFood: DbMealFood)

    @Query("DELETE FROM mealFoods WHERE id = :id")
    suspend fun deleteMealFood(id: Int)
}