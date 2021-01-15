package com.darrenfinch.mymealplanner.model.data.entities

import java.io.Serializable

data class MealPlan(
    val id: Int,
    val title: String,
    val requiredCalories: Int,
    val requiredProtein: Int,
    val requiredFat: Int,
    val requiredCarbohydrates: Int
): Serializable // TODO: Remove when view models are added