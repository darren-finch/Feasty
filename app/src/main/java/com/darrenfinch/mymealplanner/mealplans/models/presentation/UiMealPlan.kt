package com.darrenfinch.mymealplanner.mealplans.models.presentation

import java.io.Serializable

data class UiMealPlan(
    val id: Int,
    val title: String,
    val requiredCalories: Int,
    val requiredProteins: Int,
    val requiredFats: Int,
    val requiredCarbs: Int
) : Serializable