package com.darrenfinch.mymealplanner.model.room.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.darrenfinch.mymealplanner.model.room.entities.DatabaseMealPlanMeal

@Dao
interface MealPlanMealsDao {
    @Query("SELECT * FROM mealPlanMeals WHERE mealPlanId = :id")
    fun getMealsForMealPlan(id: Int) : LiveData<List<DatabaseMealPlanMeal>>

    @Query("SELECT * FROM mealPlanMeals WHERE mealPlanId = :id")
    suspend fun getMealsForMealPlanSuspended(id: Int) : List<DatabaseMealPlanMeal>

    @Query("SELECT * FROM mealPlanMeals WHERE id = :id")
    suspend fun getMealPlanMeal(id: Int) : DatabaseMealPlanMeal

    @Insert
    suspend fun insertMealPlanMeal(mealPlanMeal: DatabaseMealPlanMeal)
    @Update
    suspend fun updateMealPlanMeal(mealPlanMeal: DatabaseMealPlanMeal)
    @Delete
    suspend fun deleteMealPlanMeal(mealPlanMeal: DatabaseMealPlanMeal)
}