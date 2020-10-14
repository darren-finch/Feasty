package com.darrenfinch.mymealplanner.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.darrenfinch.mymealplanner.domain.physicalquantities.PhysicalQuantity

//This class is here to store any meal specific properties for a given food type, such as desired quantity of the food.
@Entity(tableName = "mealFoods")
data class DatabaseMealFood(
    @PrimaryKey
    val foodId: Int,
    val mealId: Int,
    val desiredServingSize: PhysicalQuantity
)