package com.darrenfinch.mymealplanner.mealplans.models.domain

import com.darrenfinch.mymealplanner.meals.models.domain.MealFood

data class MealPlanMeal(
    val id: Int,
    val mealPlanId: Int,
    val mealId: Int,
    val title: String,
    val foods: List<MealFood>
)