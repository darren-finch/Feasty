package com.darrenfinch.mymealplanner.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "foods")
data class Food(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val quantity: Double,
    val unit: MeasurementUnit,
    val calories: Int,
    val carbohydrates: Int,
    val protein: Int,
    val fat: Int)