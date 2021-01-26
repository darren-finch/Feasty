package com.darrenfinch.mymealplanner.meals.models.mappers

import com.darrenfinch.mymealplanner.foods.models.domain.MacroCalculator
import com.darrenfinch.mymealplanner.foods.models.mappers.dbMacroNutrientsToMacroNutrients
import com.darrenfinch.mymealplanner.foods.models.mappers.macroNutrientsToDbMacroNutrients
import com.darrenfinch.mymealplanner.foods.models.mappers.macroNutrientsToUiMacroNutrients
import com.darrenfinch.mymealplanner.foods.models.mappers.uiMacroNutrientsToMacroNutrients
import com.darrenfinch.mymealplanner.meals.models.domain.MealFood
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood
import com.darrenfinch.mymealplanner.model.room.models.foods.DatabaseFood
import com.darrenfinch.mymealplanner.model.room.models.meals.DatabaseMealFood

fun dbMealFoodToMealFood(dbMealFood: DatabaseMealFood, dbFoodReference: DatabaseFood) = MealFood(
    id = dbMealFood.id,
    title = dbFoodReference.title,
    mealId = dbMealFood.mealId,
    foodId = dbMealFood.foodId,
    macroNutrients = MacroCalculator.baseMacrosOnNewServingSize(
        oldMacros = dbMacroNutrientsToMacroNutrients(dbFoodReference.macroNutrients),
        oldServingSize = dbFoodReference.servingSize,
        newServingSize = dbMealFood.desiredServingSize
    ),
    desiredServingSize = dbMealFood.desiredServingSize
)

fun mealFoodToUiMealFood(mealFood: MealFood) = UiMealFood(
    id = mealFood.id,
    title = mealFood.title,
    mealId = mealFood.mealId,
    foodId = mealFood.foodId,
    macroNutrients = macroNutrientsToUiMacroNutrients(mealFood.macroNutrients),
    desiredServingSize = mealFood.desiredServingSize
)

fun uiMealFoodToMealFood(uiMealFood: UiMealFood) = MealFood(
    id = uiMealFood.id,
    title = uiMealFood.title,
    mealId = uiMealFood.mealId,
    foodId = uiMealFood.foodId,
    macroNutrients = uiMacroNutrientsToMacroNutrients(uiMealFood.macroNutrients),
    desiredServingSize = uiMealFood.desiredServingSize
)

fun mealFoodToDbMealFood(mealFood: MealFood) = DatabaseMealFood(
    id = mealFood.id,
    mealId = mealFood.mealId,
    foodId = mealFood.foodId,
    desiredServingSize = mealFood.desiredServingSize
)

// TODO: Consider a cleaner approach. Maybe simplify mappings?
fun dbMealFoodsToMealFoods(
    dbMealFoods: List<DatabaseMealFood>,
    dbFoodReferences: List<DatabaseFood?>
): List<MealFood> {
    val mealFoods = mutableListOf<MealFood>()
    for (i in 0..dbMealFoods.lastIndex) {
        if(i < dbFoodReferences.size) {
            if(dbFoodReferences[i] != null) {
                mealFoods.add(
                    dbMealFoodToMealFood(
                        dbMealFoods[i],
                        dbFoodReferences[i]!!
                    )
                )
            }
        }
    }

    return mealFoods
}