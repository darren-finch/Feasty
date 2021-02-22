package com.darrenfinch.mymealplanner.data.room.models.foods

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.darrenfinch.mymealplanner.foods.models.domain.MacroNutrients
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity

@Entity(tableName = "foods", indices = [Index("id")])
data class DbFood(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val servingSize: PhysicalQuantity,
    val macros: MacroNutrients
)