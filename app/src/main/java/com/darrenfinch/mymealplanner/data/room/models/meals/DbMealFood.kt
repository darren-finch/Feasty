package com.darrenfinch.mymealplanner.data.room.models.meals

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity

//This class is here to store any meal specific properties for a given food type, such as desired quantity of the food.
@Entity(tableName = "mealFoods")
data class DbMealFood(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val foodId: Int,
    val mealId: Int,
    val desiredServingSize: PhysicalQuantity
)