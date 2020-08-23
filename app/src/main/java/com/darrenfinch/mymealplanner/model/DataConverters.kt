package com.darrenfinch.mymealplanner.model.data

import com.darrenfinch.mymealplanner.model.room.FoodsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking

object DataConverters {
    fun convertMealFoodToFood(mealFood: MealFood) : Food {
        return Food(
            id = mealFood.id,
            title = mealFood.title,
            servingSizeUnit = mealFood.servingSizeUnit,
            servingSize = mealFood.servingSize,
            macroNutrients = MacroNutrients(
                calories = mealFood.macroNutrients.calories,
                carbohydrates = mealFood.macroNutrients.carbohydrates,
                fat = mealFood.macroNutrients.fat,
                protein = mealFood.macroNutrients.protein
            )
        )
    }
    fun convertFoodToMealFood(food: Food) : MealFood {
        return MealFood(
            id = food.id,
            title = food.title,
            servingSizeUnit = food.servingSizeUnit,
            servingSize = food.servingSize,
            macroNutrients = MacroNutrients(
                calories = food.macroNutrients.calories,
                carbohydrates = food.macroNutrients.carbohydrates,
                fat = food.macroNutrients.fat,
                protein = food.macroNutrients.protein
            ),
            quantity = food.servingSize
        )
    }
    suspend fun convertDatabaseMealToRegularMeal(
        databaseMeal: DatabaseMeal,
        foodsDao: FoodsDao
    ): Meal {
        return Meal(
            databaseMeal.id,
            databaseMeal.title,
            convertDatabaseMealFoodsToMealFoods(databaseMeal.mealFoods, foodsDao)
        )
    }
    private suspend fun convertDatabaseMealFoodsToMealFoods(
        databaseMealFoods: List<DatabaseMealFood>,
        foodsDao: FoodsDao
    ): List<MealFood> {
        return databaseMealFoods.parallelMap { convertDatabaseMealFoodToMealFood(it, foodsDao) }
    }
    private suspend fun convertDatabaseMealFoodToMealFood(
        databaseMealFood: DatabaseMealFood,
        foodsDao: FoodsDao
    ): MealFood {
        val foodFromDatabase = foodsDao.getFood(databaseMealFood.id)
        return MealFood(
            id = foodFromDatabase.id,
            title = foodFromDatabase.title,
            quantity = databaseMealFood.quantity,
            servingSizeUnit = foodFromDatabase.servingSizeUnit,
            servingSize = foodFromDatabase.servingSize,
            macroNutrients = MacroNutrients(
                calories = foodFromDatabase.macroNutrients.calories,
                carbohydrates = foodFromDatabase.macroNutrients.carbohydrates,
                fat = foodFromDatabase.macroNutrients.fat,
                protein = foodFromDatabase.macroNutrients.protein
            )
        )
    }

    fun convertMealToDatabaseMeal(meal: Meal): DatabaseMeal {
        return DatabaseMeal(
            meal.id,
            meal.title,
            meal.foods.map { convertMealFoodToDatabaseMealFood(it) }
        )
    }
    private fun convertMealFoodToDatabaseMealFood(mealFood: MealFood): DatabaseMealFood {
        return DatabaseMealFood(
            id = mealFood.id,
            quantity = mealFood.quantity
        )
    }

    private fun <A, B> List<A>.parallelMap(f: suspend (A) -> B): List<B> = runBlocking {
        map { async(Dispatchers.IO) { f(it) } }.awaitAll()
    }
}