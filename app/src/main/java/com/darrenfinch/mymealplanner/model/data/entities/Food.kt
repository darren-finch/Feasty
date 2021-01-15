package com.darrenfinch.mymealplanner.model.data.entities

import com.darrenfinch.mymealplanner.model.data.entitysubdata.MacroNutrients
import com.darrenfinch.mymealplanner.domain.physicalquantities.PhysicalQuantity
import java.io.Serializable

data class Food(
    val id: Int = 0,
    val title: String,
    val servingSize: PhysicalQuantity,
    val macroNutrients: MacroNutrients
): Serializable // TODO: REMOVE SERIALIZABLE AND USE VIEWMODEL