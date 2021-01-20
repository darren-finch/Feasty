package com.darrenfinch.mymealplanner.foods.models.presentation

data class UiMacroNutrients(
    val calories: Int,
    val carbs: Int,
    val proteins: Int,
    val fats: Int
) {
    override fun toString() = "$calories calories | ${proteins}P | ${carbs}C | ${fats}F"
}