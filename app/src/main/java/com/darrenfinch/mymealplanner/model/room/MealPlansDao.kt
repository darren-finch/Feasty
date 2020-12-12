package com.darrenfinch.mymealplanner.model.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface MealPlansDao {
    @Query("SELECT * FROM mealPlans WHERE :id = id")
    fun getMealPlan(id: Int): LiveData<DatabaseMealPlan>
}