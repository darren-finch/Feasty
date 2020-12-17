package com.darrenfinch.mymealplanner.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mealPlanMeals")
data class DatabaseMealPlanMeal(@PrimaryKey(autoGenerate = true) val id: Int, val mealPlanId: Int, val mealId: Int)