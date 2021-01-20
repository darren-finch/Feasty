package com.darrenfinch.mymealplanner.meals.models

import java.io.Serializable

data class Meal(
    val id: Int,
    val title: String,
    val foods: List<MealFood>
) : Serializable