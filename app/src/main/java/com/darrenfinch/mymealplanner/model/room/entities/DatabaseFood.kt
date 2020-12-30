package com.darrenfinch.mymealplanner.model.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.darrenfinch.mymealplanner.domain.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.model.data.entitysubdata.MacroNutrients
import com.darrenfinch.mymealplanner.model.data.entitysubdata.MetricUnit

@Entity(tableName = "foods")
data class DatabaseFood(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val servingSize: PhysicalQuantity,
    val macroNutrients: MacroNutrients
)