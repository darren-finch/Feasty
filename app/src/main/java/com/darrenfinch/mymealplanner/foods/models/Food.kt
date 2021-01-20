package com.darrenfinch.mymealplanner.foods.models

import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity
import java.io.Serializable

data class Food(
    val id: Int = 0,
    val title: String,
    val servingSize: PhysicalQuantity,
    val macroNutrients: MacroNutrients
): Serializable // TODO: REMOVE SERIALIZABLE AND USE VIEWMODEL