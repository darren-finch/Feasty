package com.darrenfinch.mymealplanner.model.room.models.mealplans

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mealPlans")
data class DatabaseMealPlan(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val requiredCalories: Int,
    val requiredProteins: Int,
    val requiredFats: Int,
    val requiredCarbohydrates: Int
)