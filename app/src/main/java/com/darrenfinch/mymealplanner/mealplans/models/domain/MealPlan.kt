package com.darrenfinch.mymealplanner.mealplans.models.domain

data class MealPlan(
    val id: Int,
    val title: String,
    val requiredCalories: Int,
    val requiredProteins: Int,
    val requiredFats: Int,
    val requiredCarbohydrates: Int
)