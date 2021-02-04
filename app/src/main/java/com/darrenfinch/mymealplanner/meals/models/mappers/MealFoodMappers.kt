package com.darrenfinch.mymealplanner.meals.models.mappers

import com.darrenfinch.mymealplanner.foods.models.mappers.dbMacroNutrientsToMacroNutrients
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
    originalMacroNutrients = dbMacroNutrientsToMacroNutrients(dbFoodReference.macroNutrients),
    desiredServingSize = dbMealFood.desiredServingSize,
    originalServingSize = dbFoodReference.servingSize
)

fun mealFoodToUiMealFood(mealFood: MealFood) = UiMealFood(
    id = mealFood.id,
    title = mealFood.title,
    mealId = mealFood.mealId,
    foodId = mealFood.foodId,
    originalMacroNutrients = macroNutrientsToUiMacroNutrients(mealFood.originalMacroNutrients),
    desiredServingSize = mealFood.desiredServingSize,
    originalServingSize = mealFood.originalServingSize
)

fun uiMealFoodToMealFood(uiMealFood: UiMealFood) = MealFood(
    id = uiMealFood.id,
    title = uiMealFood.title,
    mealId = uiMealFood.mealId,
    foodId = uiMealFood.foodId,
    originalMacroNutrients = uiMacroNutrientsToMacroNutrients(uiMealFood.originalMacroNutrients),
    desiredServingSize = uiMealFood.desiredServingSize,
    originalServingSize = uiMealFood.originalServingSize
)

fun mealFoodToDbMealFood(mealFood: MealFood) = DatabaseMealFood(
    id = mealFood.id,
    mealId = mealFood.mealId,
    foodId = mealFood.foodId,
    desiredServingSize = mealFood.desiredServingSize
)

@Suppress("UnnecessaryVariable")
fun dbMealFoodsToMealFoods(
    dbMealFoods: List<DatabaseMealFood>,
    dbFoodReferences: List<DatabaseFood?>
): List<MealFood> {
    val dbMealFoodsArray = dbMealFoods.toTypedArray()
    val dbMealFoodArray = dbFoodReferences.toTypedArray()

    val mealFoods = dbMealFoodsArray.mapIndexedNotNull { i, dbMealFood ->
        val dbFoodReference = dbMealFoodArray.getOrNull(i)
        dbFoodReference?.let {
            dbMealFoodToMealFood(
                dbMealFood,
                it
            )
        }
    }

    return mealFoods
}