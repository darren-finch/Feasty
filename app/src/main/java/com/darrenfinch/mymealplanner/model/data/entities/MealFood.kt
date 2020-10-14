package com.darrenfinch.mymealplanner.model.data.entities

import com.darrenfinch.mymealplanner.model.data.entitysubdata.MacroNutrients
import com.darrenfinch.mymealplanner.domain.physicalquantities.PhysicalQuantity
import java.io.Serializable

/**
 * MealFood is very closely related to Food. The main difference is that a meal food belongs to a meal. A food is its own entity.
 * Meal foods
 */
data class MealFood(
    val id: Int = 0,
    val title: String,
    val desiredServingSize: PhysicalQuantity,
    val servingSize: PhysicalQuantity, //serving size is a mirror reference to the food this
    val macroNutrients: MacroNutrients
) : Serializable //mealFood is passed around in a nav args bundle, so this is necessary