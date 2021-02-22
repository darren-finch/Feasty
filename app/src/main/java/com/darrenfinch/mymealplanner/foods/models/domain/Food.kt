package com.darrenfinch.mymealplanner.foods.models.domain

import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity

data class Food(
    val id: Int = 0,
    val title: String,
    val servingSize: PhysicalQuantity,
    val macros: MacroNutrients
)