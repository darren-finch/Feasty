package com.darrenfinch.mymealplanner.model.data

data class Meal(val id: Int,
                val title: String,
                val foods: List<MealFood>)