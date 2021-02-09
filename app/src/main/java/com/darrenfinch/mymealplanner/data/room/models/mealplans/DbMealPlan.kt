package com.darrenfinch.mymealplanner.data.room.models.mealplans

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mealPlans")
data class DbMealPlan(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val requiredCalories: Int,
    val requiredProteins: Int,
    val requiredFats: Int,
    val requiredCarbohydrates: Int
)