package com.darrenfinch.mymealplanner.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.darrenfinch.mymealplanner.model.room.MeasurementUnit

@Entity(tableName = "foods")
data class Food(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val servingSize: Double,
    val servingSizeUnit: MeasurementUnit,
    val calories: Int,
    val carbohydrates: Int,
    val protein: Int,
    val fat: Int
)