package com.darrenfinch.mymealplanner.model.data.entities

import com.darrenfinch.mymealplanner.model.data.entitysubdata.MacroNutrients
import com.darrenfinch.mymealplanner.model.data.entitysubdata.MetricUnit

data class Food(
    val id: Int = 0,
    val title: String,
    val servingSize: Double,
    val servingSizeUnit: MetricUnit,
    val macroNutrients: MacroNutrients
)