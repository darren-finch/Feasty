package com.darrenfinch.mymealplanner.data.room.models.mealplans

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "mealPlans", indices = [Index("id")])
data class DbMealPlan(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val requiredCalories: Int,
    val requiredProteins: Int,
    val requiredFats: Int,
    val requiredCarbohydrates: Int
)