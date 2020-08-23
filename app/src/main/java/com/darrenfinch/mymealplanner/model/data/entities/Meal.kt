package com.darrenfinch.mymealplanner.model.data.entities

import java.io.Serializable

data class Meal(
    val id: Int,
    val title: String,
    val foods: List<MealFood>
) : Serializable