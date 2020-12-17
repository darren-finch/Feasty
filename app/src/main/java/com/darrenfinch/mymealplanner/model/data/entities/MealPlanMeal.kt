package com.darrenfinch.mymealplanner.model.data.entities

data class MealPlanMeal(val id: Int, val mealPlanId: Int, val mealId: Int, val title: String, val foods: List<MealFood>)