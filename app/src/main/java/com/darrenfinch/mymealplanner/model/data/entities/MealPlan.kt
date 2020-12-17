package com.darrenfinch.mymealplanner.model.data.entities

data class MealPlan(val id: Int, val title: String, val requiredCalories: Int, val requiredProtein: Int, val requiredFat: Int, val requiredCarbohydrates: Int)