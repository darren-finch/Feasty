package com.darrenfinch.mymealplanner.meals.models.mappers

import com.darrenfinch.mymealplanner.data.room.models.foods.DbFood
import com.darrenfinch.mymealplanner.data.room.models.meals.DbMealFood
import com.darrenfinch.mymealplanner.meals.models.domain.MealFood
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood

fun dbMealFoodToMealFood(dbMealFood: DbMealFood, dbFoodReference: DbFood) = MealFood(
    id = dbMealFood.id,
    title = dbFoodReference.title,
    mealId = dbMealFood.mealOwnerId,
    foodId = dbMealFood.referencedFoodId,
    originalMacros = dbFoodReference.macros,
    desiredServingSize = dbMealFood.desiredServingSize,
    originalServingSize = dbFoodReference.servingSize
)

fun mealFoodToUiMealFood(mealFood: MealFood) = UiMealFood(
    id = mealFood.id,
    title = mealFood.title,
    mealId = mealFood.mealId,
    foodId = mealFood.foodId,
    originalMacros = mealFood.originalMacros,
    desiredServingSize = mealFood.desiredServingSize,
    originalServingSize = mealFood.originalServingSize
)

fun uiMealFoodToMealFood(uiMealFood: UiMealFood) = MealFood(
    id = uiMealFood.id,
    title = uiMealFood.title,
    mealId = uiMealFood.mealId,
    foodId = uiMealFood.foodId,
    originalMacros = uiMealFood.originalMacros,
    desiredServingSize = uiMealFood.desiredServingSize,
    originalServingSize = uiMealFood.originalServingSize
)

fun mealFoodToDbMealFood(mealFood: MealFood) = DbMealFood(
    id = mealFood.id,
    mealOwnerId = mealFood.mealId,
    referencedFoodId = mealFood.foodId,
    desiredServingSize = mealFood.desiredServingSize
)

@Suppress("UnnecessaryVariable")
fun dbMealFoodsToMealFoods(
    dbMealFoods: List<DbMealFood>,
    dbFoodReferences: List<DbFood?>
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