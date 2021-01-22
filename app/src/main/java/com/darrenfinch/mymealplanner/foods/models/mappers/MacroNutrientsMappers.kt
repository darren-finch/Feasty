package com.darrenfinch.mymealplanner.foods.models.mappers

import com.darrenfinch.mymealplanner.foods.models.domain.MacroNutrients
import com.darrenfinch.mymealplanner.foods.models.presentation.UiMacroNutrients
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import com.darrenfinch.mymealplanner.model.room.models.foods.DatabaseMacroNutrients

fun dbMacroNutrientsToMacroNutrients(dbMacroNutrients: DatabaseMacroNutrients) = MacroNutrients(
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

fun macroNutrientsToDbMacroNutrients(macroNutrients: MacroNutrients) = DatabaseMacroNutrients(
    calories = macroNutrients.calories,
    carbs = macroNutrients.carbs,
    proteins = macroNutrients.proteins,
    fats = macroNutrients.fats
)