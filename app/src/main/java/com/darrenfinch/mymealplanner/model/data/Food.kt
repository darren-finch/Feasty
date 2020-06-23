package com.darrenfinch.mymealplanner.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "foods")
data class Food(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val servingSize: Double,
    val servingSizeUnit: MetricUnit,
    val macroNutrients: MacroNutrients
)