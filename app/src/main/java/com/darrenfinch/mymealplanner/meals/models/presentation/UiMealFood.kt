package com.darrenfinch.mymealplanner.meals.models.presentation

import com.darrenfinch.mymealplanner.foods.models.domain.MacroNutrients
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity
import java.io.Serializable

data class UiMealFood(
    val id: Int = 0,
    val foodId: Int = 0,
    val mealId: Int = 0,
    val title: String,
    val desiredServingSize: PhysicalQuantity,
    // the macro nutrients based on desired serving size are calculated on-the-fly
    val originalServingSize: PhysicalQuantity,
    val originalMacros: MacroNutrients,
) : Serializable