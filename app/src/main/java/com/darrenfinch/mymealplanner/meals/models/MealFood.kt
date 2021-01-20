package com.darrenfinch.mymealplanner.meals.models

import com.darrenfinch.mymealplanner.foods.models.MacroNutrients
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity
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