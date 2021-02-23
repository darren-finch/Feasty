package com.darrenfinch.mymealplanner.foods.models.domain

import java.io.Serializable

data class MacroNutrients(
    val calories: Int,
    val carbs: Int,
    val proteins: Int,
    val fats: Int
): Serializable {
    override fun toString() = "$calories calories | ${proteins}P | ${carbs}C | ${fats}F"
}