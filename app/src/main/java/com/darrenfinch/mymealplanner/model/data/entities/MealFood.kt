package com.darrenfinch.mymealplanner.model.data.entities

import com.darrenfinch.mymealplanner.model.data.entitysubdata.MacroNutrients
import com.darrenfinch.mymealplanner.model.data.entitysubdata.MetricUnit
import com.darrenfinch.physicalquantities.PhysicalQuantity
import java.io.Serializable

data class MealFood(
    val id: Int = 0,
    val title: String,
    val desiredServingSize: PhysicalQuantity,
    val servingSize: PhysicalQuantity,
    val macroNutrients: MacroNutrients
) : Serializable //mealFood is passed around in a nav args bundle, so this is necessary