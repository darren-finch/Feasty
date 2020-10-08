package com.darrenfinch.mymealplanner.model.data.entities

import com.darrenfinch.mymealplanner.model.data.entitysubdata.MacroNutrients
import com.darrenfinch.mymealplanner.model.data.entitysubdata.MetricUnit
import com.darrenfinch.physicalquantities.PhysicalQuantity

data class Food(
    val id: Int = 0,
    val title: String,
    val servingSize: PhysicalQuantity,
    val macroNutrients: MacroNutrients
)