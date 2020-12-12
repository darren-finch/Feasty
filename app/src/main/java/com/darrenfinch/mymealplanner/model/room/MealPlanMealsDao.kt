package com.darrenfinch.mymealplanner.model.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface MealPlanMealsDao {
    @Query("SELECT * FROM mealPlanMeals WHERE mealId = :id")
    fun getMealFromMealId(id: Int) : LiveData<DatabaseMealPlanMeal>
    @Query("SELECT * FROM mealPlanMeals WHERE mealPlanId = :id")
    suspend fun getMealsFromMealPlanId(id: Int) : List<DatabaseMealPlanMeal>
}