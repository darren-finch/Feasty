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
            calories = mealFood.calories,
            carbohydrates = mealFood.carbohydrates,
            fat = mealFood.fat,
            protein = mealFood.protein
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
            calories = foodFromDatabase.calories,
            carbohydrates = foodFromDatabase.carbohydrates,
            fat = foodFromDatabase.fat,
            protein = foodFromDatabase.protein
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