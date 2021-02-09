package com.darrenfinch.mymealplanner.data.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.darrenfinch.mymealplanner.data.room.models.mealplans.DbMealPlan

@Dao
interface MealPlansDao {
    @Query("SELECT * FROM mealPlans")
    suspend fun getAllMealPlans(): List<DbMealPlan>

    @Query("SELECT * FROM mealPlans WHERE :id = id")
    suspend fun getMealPlan(id: Int): DbMealPlan?

    @Insert
    suspend fun insertMealPlan(mealPlan: DbMealPlan)

    @Update
    suspend fun updateMealPlan(mealPlan: DbMealPlan)

    @Query("DELETE FROM mealPlans WHERE id = :id")
    suspend fun deleteMealPlan(id: Int)
}