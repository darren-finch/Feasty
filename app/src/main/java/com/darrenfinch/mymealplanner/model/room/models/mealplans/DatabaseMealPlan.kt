package com.darrenfinch.mymealplanner.model.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mealPlans")
data class DatabaseMealPlan(@PrimaryKey(autoGenerate = true) val id: Int, val title: String, val requiredCalories: Int, val requiredProtein: Int, val requiredFat: Int, val requiredCarbohydrates: Int)