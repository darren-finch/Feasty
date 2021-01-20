package com.darrenfinch.mymealplanner.mealplans.models

import java.io.Serializable

data class MealPlan(
    val id: Int,
    val title: String,
    val requiredCalories: Int,
    val requiredProteins: Int,
    val requiredFats: Int,
    val requiredCarbohydrates: Int
): Serializable // TODO: Remove when view models are added