package com.darrenfinch.mymealplanner.model.data.entities

import com.darrenfinch.mymealplanner.model.data.entitysubdata.MacroNutrients
import com.darrenfinch.mymealplanner.domain.physicalquantities.PhysicalQuantity
import java.io.Serializable

/**
 * MealFood is very closely related to Food. The main difference is that a meal food belongs to a meal. A food is its own entity.
 */
data class MealFood(
    val id: Int = 0,
    val foodId: Int = 0,
    val mealId: Int = 0,
    val title: String,
    val desiredServingSize: PhysicalQuantity,
    val macroNutrients: MacroNutrients
) : Serializable //mealFood is passed around in a nav args bundle, so this is necessary