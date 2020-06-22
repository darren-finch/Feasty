package com.darrenfinch.mymealplanner.model.data

import androidx.room.PrimaryKey
import com.darrenfinch.mymealplanner.model.room.MeasurementUnit

data class MealFood(
    val id: Int = 0,
    val title: String,
    val quantity: Double,
    val servingSize: Double,
    val servingSizeUnit: MeasurementUnit,
    val calories: Int,
    val carbohydrates: Int,
    val protein: Int,
    val fat: Int
)