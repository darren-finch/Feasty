package com.darrenfinch.mymealplanner.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "foods")
data class Food(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val quantity: Double,
    val quantityUnit: MeasurementUnit,
    val calories: Int,
    val carbohydrates: Int,
    val protein: Int,
    val fat: Int)