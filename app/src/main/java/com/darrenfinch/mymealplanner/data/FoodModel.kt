package com.darrenfinch.mymealplanner.data

data class FoodModel(var title: String, var quantity: Double, var unit: MeasurementUnit, var calories: Int, var carbohydrates: Int, var protein: Int, var fat: Int)