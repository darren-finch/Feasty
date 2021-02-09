package com.darrenfinch.mymealplanner.data.room.models.foods

data class DbMacroNutrients(
    val calories: Int,
    val carbs: Int,
    val proteins: Int,
    val fats: Int
) {
    override fun toString() = "$calories calories | ${proteins}P | ${carbs}C | ${fats}F"
}
