package com.darrenfinch.mymealplanner.model.data

data class MealFood(
    val id: Int = 0,
    val title: String,
    val quantity: Double,
    val servingSize: Double,
    val servingSizeUnit: MetricUnit,
    val calories: Int,
    val carbohydrates: Int,
    val protein: Int,
    val fat: Int
)