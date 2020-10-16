package com.darrenfinch.mymealplanner.model

import com.darrenfinch.mymealplanner.model.data.entities.Food
import com.darrenfinch.mymealplanner.model.data.entities.Meal
import com.darrenfinch.mymealplanner.model.data.entities.MealFood
import com.darrenfinch.mymealplanner.model.data.entitysubdata.MacroNutrients
import com.darrenfinch.mymealplanner.model.helpers.MacroCalculator
import com.darrenfinch.mymealplanner.model.room.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking

object DataConverters {
    fun convertMealFoodToFood(mealFood: MealFood): Food {
        return Food(
            id = mealFood.id,
            title = mealFood.title,
            servingSize = mealFood.desiredServingSize,
            macroNutrients = MacroNutrients(
                calories = mealFood.macroNutrients.calories,
                carbs = mealFood.macroNutrients.carbs,
                fat = mealFood.macroNutrients.fat,
                protein = mealFood.macroNutrients.protein
            )
        )
    }

    fun convertFoodToMealFood(food: Food): MealFood {
        return MealFood(
            id = food.id,
            title = food.title,
            macroNutrients = MacroNutrients(
                calories = food.macroNutrients.calories,
                carbs = food.macroNutrients.carbs,
                fat = food.macroNutrients.fat,
                protein = food.macroNutrients.protein
            ),
            desiredServingSize = food.servingSize
        )
    }

    fun convertMealFoodToDatabaseMealFood(mealFood: MealFood, mealId: Int): DatabaseMealFood {
        return DatabaseMealFood(mealFood.id, mealId, mealFood.desiredServingSize)
    }

    fun convertDatabaseFoodToFood(databaseFood: DatabaseFood): Food {
        return Food(
            databaseFood.id,
            databaseFood.title,
            databaseFood.servingSize,
            databaseFood.macroNutrients
        )
    }

    fun convertFoodToDatabaseFood(food: Food): DatabaseFood {
        return DatabaseFood(
            food.id,
            food.title,
            food.servingSize,
            food.macroNutrients
        )
    }

    suspend fun convertDatabaseMealToRegularMeal(
        databaseMeal: DatabaseMeal,
        mealFoodsDao: MealFoodsDao,
        foodsDao: FoodsDao
    ): Meal {
        return Meal(
            databaseMeal.id,
            databaseMeal.title,
            convertDatabaseMealFoodsToMealFoods(
                mealFoodsDao.getMealFoodsForMeal(databaseMeal.id),
                foodsDao
            )
        )
    }

    private suspend fun convertDatabaseMealFoodsToMealFoods(
        databaseMealFoods: List<DatabaseMealFood>,
        foodsDao: FoodsDao
    ): List<MealFood> {
        return databaseMealFoods.parallelMap {
            convertDatabaseMealFoodToMealFood(
                it,
                foodsDao
            )
        }
    }

    private suspend fun convertDatabaseMealFoodToMealFood(
        databaseMealFood: DatabaseMealFood,
        foodsDao: FoodsDao
    ): MealFood {
        val foodFromDatabase = foodsDao.getFood(databaseMealFood.foodId)

        return MacroCalculator.updateMacrosForMealFoodWithNewServingSize(MealFood(
            id = foodFromDatabase.id,
            title = foodFromDatabase.title,
            desiredServingSize = foodFromDatabase.servingSize,
            macroNutrients = MacroNutrients(
                calories = foodFromDatabase.macroNutrients.calories,
                carbs = foodFromDatabase.macroNutrients.carbs,
                fat = foodFromDatabase.macroNutrients.fat,
                protein = foodFromDatabase.macroNutrients.protein
            )
        ), databaseMealFood.desiredServingSize)
    }

    fun convertMealToDatabaseMeal(meal: Meal): DatabaseMeal {
        return DatabaseMeal(
            meal.id,
            meal.title
        )
    }

    private fun <A, B> List<A>.parallelMap(f: suspend (A) -> B): List<B> = runBlocking {
        map { async(Dispatchers.IO) { f(it) } }.awaitAll()
    }
}