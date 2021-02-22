package com.darrenfinch.mymealplanner.foods.models.domain

data class MacroNutrients(
    val calories: Int,
    val carbs: Int,
    val proteins: Int,
    val fats: Int
) {
    override fun toString() = "$calories calories | ${proteins}P | ${carbs}C | ${fats}F"
}