package com.darrenfinch.mymealplanner.data.room.models.mealplans

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.darrenfinch.mymealplanner.data.room.models.foods.DbFood
import com.darrenfinch.mymealplanner.data.room.models.meals.DbMeal

@Entity(
    tableName = "mealPlanMeals",
    foreignKeys = [
        ForeignKey(
            entity = DbMealPlan::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("mealPlanOwnerId"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = DbMeal::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("referenceMealId"),
            onDelete = ForeignKey.CASCADE
        )
    ],
)
data class DbMealPlanMeal(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val mealPlanOwnerId: Int,
    val referenceMealId: Int
)