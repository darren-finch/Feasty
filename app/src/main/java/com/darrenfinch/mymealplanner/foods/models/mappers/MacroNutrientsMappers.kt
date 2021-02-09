package com.darrenfinch.mymealplanner.foods.models.mappers

import com.darrenfinch.mymealplanner.foods.models.domain.MacroNutrients
import com.darrenfinch.mymealplanner.foods.models.presentation.UiMacroNutrients
import com.darrenfinch.mymealplanner.data.room.models.foods.DbMacroNutrients

fun dbMacroNutrientsToMacroNutrients(dbMacroNutrients: DbMacroNutrients) = MacroNutrients(
    calories = dbMacroNutrients.calories,
    carbs = dbMacroNutrients.carbs,
    proteins = dbMacroNutrients.proteins,
    fats = dbMacroNutrients.fats
)

fun macroNutrientsToUiMacroNutrients(macroNutrients: MacroNutrients) = UiMacroNutrients(
    calories = macroNutrients.calories,
    carbs = macroNutrients.carbs,
    proteins = macroNutrients.proteins,
    fats = macroNutrients.fats
)

fun uiMacroNutrientsToMacroNutrients(uiMacroNutrients: UiMacroNutrients) = MacroNutrients(
    calories = uiMacroNutrients.calories,
    carbs = uiMacroNutrients.carbs,
    proteins = uiMacroNutrients.proteins,
    fats = uiMacroNutrients.fats
)

fun macroNutrientsToDbMacroNutrients(macroNutrients: MacroNutrients) = DbMacroNutrients(
    calories = macroNutrients.calories,
    carbs = macroNutrients.carbs,
    proteins = macroNutrients.proteins,
    fats = macroNutrients.fats
)