package com.darrenfinch.mymealplanner.model.room

data class Meal(val id: Int,
                val title: String,
                val foods: List<Food>)