package com.darrenfinch.mymealplanner.data.room.models.mealplans

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mealPlanMeals")
data class DbMealPlanMeal(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val mealPlanId: Int,
    val mealId: Int
)