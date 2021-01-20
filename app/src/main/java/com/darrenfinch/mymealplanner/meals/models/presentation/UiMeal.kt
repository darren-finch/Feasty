package com.darrenfinch.mymealplanner.meals.models.presentation

import java.io.Serializable

data class UiMeal(
    val id: Int,
    val title: String,
    val foods: List<UiMealFood>
) : Serializable