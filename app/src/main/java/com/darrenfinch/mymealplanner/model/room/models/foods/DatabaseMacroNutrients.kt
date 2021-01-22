package com.darrenfinch.mymealplanner.model.room.models.foods

data class DatabaseMacroNutrients(
    val calories: Int,
    val carbs: Int,
    val proteins: Int,
    val fats: Int
) {
    override fun toString() = "$calories calories | ${proteins}P | ${carbs}C | ${fats}F"
}
