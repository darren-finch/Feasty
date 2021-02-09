package com.darrenfinch.mymealplanner.data.room.daos

import androidx.room.*
import com.darrenfinch.mymealplanner.data.room.models.mealplans.DbMealPlanMeal

@Dao
interface MealPlanMealsDao {
    @Query("SELECT * FROM mealPlanMeals WHERE mealPlanId = :mealPlanId")
    suspend fun getMealsForMealPlan(mealPlanId: Int): List<DbMealPlanMeal>

    @Query("SELECT * FROM mealPlanMeals WHERE id = :id")
    suspend fun getMealPlanMeal(id: Int): DbMealPlanMeal

    @Insert
    suspend fun insertMealPlanMeal(mealPlanMeal: DbMealPlanMeal)

    @Update
    suspend fun updateMealPlanMeal(mealPlanMeal: DbMealPlanMeal)

    @Query("DELETE FROM mealPlanMeals WHERE id = :id")
    suspend fun deleteMealPlanMeal(id: Int)
}