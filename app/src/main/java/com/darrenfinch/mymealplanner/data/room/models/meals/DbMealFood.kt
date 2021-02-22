package com.darrenfinch.mymealplanner.data.room.models.meals

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.darrenfinch.mymealplanner.data.room.models.foods.DbFood
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity

//This class is here to store any meal specific properties for a given food type, such as desired quantity of the food.
@Entity(
    tableName = "mealFoods",
    foreignKeys = [
        ForeignKey(
            entity = DbFood::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("referencedFoodId"),
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = DbMeal::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("mealOwnerId"),
            onDelete = CASCADE
        )
    ]
)
data class DbMealFood(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val referencedFoodId: Int,
    val mealOwnerId: Int,
    val desiredServingSize: PhysicalQuantity
)