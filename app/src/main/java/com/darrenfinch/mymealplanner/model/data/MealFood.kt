package com.darrenfinch.mymealplanner.model.data

import java.io.Serializable

data class MealFood(
    val id: Int = 0,
    val title: String,
    val quantity: Double,
    val servingSize: Double,
    val servingSizeUnit: MetricUnit,
    val macroNutrients: MacroNutrients
) : Serializable //mealFood is passed around in a nav args bundle, so this is necessary