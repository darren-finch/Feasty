package com.darrenfinch.mymealplanner.model.room

import androidx.room.Entity

@Entity(tableName = "mealPlanMeals")
data class DatabaseMealPlanMeal(val mealPlanId: Int, val mealId: Int)