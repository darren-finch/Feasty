package com.darrenfinch.mymealplanner.foods.models.mappers

import com.darrenfinch.mymealplanner.foods.models.domain.Food
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood
import com.darrenfinch.mymealplanner.data.room.models.foods.DbFood

fun dbFoodToFood(dbFood: DbFood) = Food(
    id = dbFood.id,
    title = dbFood.title,
    servingSize = dbFood.servingSize,
    macroNutrients = dbMacroNutrientsToMacroNutrients(dbFood.macroNutrients)
)

fun foodToUiFood(food: Food) = UiFood(
    id = food.id,
    title = food.title,
    servingSize = food.servingSize,
    macroNutrients = macroNutrientsToUiMacroNutrients(food.macroNutrients)
)

fun uiFoodToFood(uiFood: UiFood) = Food(
    id = uiFood.id,
    title = uiFood.title,
    servingSize = uiFood.servingSize,
    macroNutrients = uiMacroNutrientsToMacroNutrients(uiFood.macroNutrients)
)

fun foodToDbFood(food: Food) = DbFood(
    id = food.id,
    title = food.title,
    servingSize = food.servingSize,
    macroNutrients = macroNutrientsToDbMacroNutrients(food.macroNutrients)
)