package com.darrenfinch.mymealplanner.model.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.darrenfinch.mymealplanner.model.room.models.mealplans.DatabaseMealPlan

@Dao
interface MealPlansDao {
    @Query("SELECT * FROM mealPlans")
    suspend fun getAllMealPlans(): List<DatabaseMealPlan>

    @Query("SELECT * FROM mealPlans WHERE :id = id")
    suspend fun getMealPlan(id: Int): DatabaseMealPlan

    @Insert
    suspend fun insertMealPlan(mealPlan: DatabaseMealPlan)

    @Update
    suspend fun updateMealPlan(mealPlan: DatabaseMealPlan)

    @Query("DELETE FROM mealPlans WHERE id = :id")
    suspend fun deleteMealPlan(id: Int)
}