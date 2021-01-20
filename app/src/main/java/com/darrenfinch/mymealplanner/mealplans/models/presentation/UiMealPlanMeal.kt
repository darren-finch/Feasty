package com.darrenfinch.mymealplanner.mealplans.models.presentation

import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood
import java.io.Serializable

data class UiMealPlanMeal(
    val id: Int,
    val mealPlanId: Int,
    val mealId: Int,
    val title: String,
    val foods: List<UiMealFood>
) : Serializable