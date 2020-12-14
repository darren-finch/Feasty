package com.darrenfinch.mymealplanner.model.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.darrenfinch.mymealplanner.model.data.entities.MealPlanMeal

@Dao
interface MealPlanMealsDao {
    @Query("SELECT * FROM mealPlanMeals WHERE mealPlanId = :id")
    fun getMealsForMealPlan(id: Int) : LiveData<List<DatabaseMealPlanMeal>>

    @Query("SELECT * FROM mealPlanMeals WHERE id = :id")
    suspend fun getMealPlanMeal(id: Int) : DatabaseMealPlanMeal

    @Insert
    suspend fun insertMealPlanMeal(mealPlanMeal: MealPlanMeal)
    @Update
    suspend fun updateMealPlanMeal(mealPlanMeal: MealPlanMeal)
    @Delete
    suspend fun deleteMealPlanMeal(mealPlanMeal: MealPlanMeal)
}