package com.darrenfinch.mymealplanner.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.darrenfinch.mymealplanner.model.data.entitysubdata.MacroNutrients
import com.darrenfinch.mymealplanner.model.data.entitysubdata.MetricUnit

@Entity(tableName = "foods")
data class DatabaseFood(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val servingSize: Double,
    val servingSizeUnit: MetricUnit,
    val macroNutrients: MacroNutrients
)