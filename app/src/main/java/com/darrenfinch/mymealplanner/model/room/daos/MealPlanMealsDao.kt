package com.darrenfinch.mymealplanner.model.room.daos

import androidx.room.*
import com.darrenfinch.mymealplanner.model.room.models.mealplans.DatabaseMealPlanMeal

@Dao
interface MealPlanMealsDao {
    @Query("SELECT * FROM mealPlanMeals WHERE mealPlanId = :mealPlanId")
    suspend fun getMealsForMealPlan(mealPlanId: Int): List<DatabaseMealPlanMeal>

    @Query("SELECT * FROM mealPlanMeals WHERE id = :id")
    suspend fun getMealPlanMeal(id: Int): DatabaseMealPlanMeal

    @Insert
    suspend fun insertMealPlanMeal(mealPlanMeal: DatabaseMealPlanMeal)

    @Update
    suspend fun updateMealPlanMeal(mealPlanMeal: DatabaseMealPlanMeal)

    @Query("DELETE FROM mealPlanMeals WHERE id = :id")
    suspend fun deleteMealPlanMeal(id: Int)
}