package com.darrenfinch.mymealplanner.foods.models.presentation

import com.darrenfinch.mymealplanner.foods.models.domain.MacroNutrients
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity
import java.io.Serializable

data class UiFood(
    val id: Int = 0,
    val title: String,
    val servingSize: PhysicalQuantity,
    val macros: MacroNutrients
): Serializable