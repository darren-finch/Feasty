package com.darrenfinch.mymealplanner.model.room

import androidx.room.Entity

@Entity(tableName = "mealPlans")
data class DatabaseMealPlan(val id: Int)