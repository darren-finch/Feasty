package com.darrenfinch.mymealplanner.model.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.foods.models.MacroNutrients

@Entity(tableName = "foods")
data class DatabaseFood(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val servingSize: PhysicalQuantity,
    val macroNutrients: MacroNutrients
)