package com.darrenfinch.mymealplanner.model.data.entitysubdata

data class MacroNutrients (
    val calories: Int,
    val carbs: Int,
    val protein: Int,
    val fat: Int
) {
    override fun toString() = "$calories calories | ${protein}P | ${carbs}C | ${fat}F"
}