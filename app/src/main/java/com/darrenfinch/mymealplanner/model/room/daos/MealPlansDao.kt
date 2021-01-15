package com.darrenfinch.mymealplanner.model.room.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.darrenfinch.mymealplanner.model.room.entities.DatabaseMealPlan

@Dao
interface MealPlansDao {
    @Query("SELECT * FROM mealPlans")
    fun getMealPlans(): LiveData<List<DatabaseMealPlan>>

    @Query("SELECT * FROM mealPlans WHERE :id = id")
    suspend fun getMealPlan(id: Int): DatabaseMealPlan

    @Insert
    suspend fun insertMealPlan(mealPlan: DatabaseMealPlan)

    @Update
    suspend fun updateMealPlan(mealPlan: DatabaseMealPlan)

    @Query("DELETE FROM mealPlans WHERE id = :id")
    suspend fun deleteMealPlan(id: Int)
}