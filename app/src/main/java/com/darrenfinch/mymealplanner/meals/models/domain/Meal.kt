package com.darrenfinch.mymealplanner.meals.models.domain

data class Meal(
    val id: Int,
    val title: String,
    val foods: List<MealFood>
)