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

    //This is a hack (should be in FoodsDAO), but I'm not going to invest the effort to do this properly atm.
    @Query("SELECT * FROM foods WHERE id IN (:mealFoodIds)")
    suspend fun getMealFoodsWithIds(mealFoodIds: List<Int>) : List<Food>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: DatabaseMeal)

    @Update
    suspend fun updateMeal(meal: DatabaseMeal)

    @Delete
    suspend fun deleteMeal(meal: DatabaseMeal)
}