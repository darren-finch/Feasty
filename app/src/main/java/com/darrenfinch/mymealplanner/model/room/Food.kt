package com.darrenfinch.mymealplanner.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.darrenfinch.mymealplanner.model.helpers.MeasurementUnit

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