package com.darrenfinch.mymealplanner.model

import com.darrenfinch.mymealplanner.model.data.entities.*
import com.darrenfinch.mymealplanner.model.data.entitysubdata.MacroNutrients
import com.darrenfinch.mymealplanner.model.helpers.MacroCalculator
import com.darrenfinch.mymealplanner.model.room.daos.FoodsDao
import com.darrenfinch.mymealplanner.model.room.daos.MealFoodsDao
import com.darrenfinch.mymealplanner.model.room.daos.MealsDao
import com.darrenfinch.mymealplanner.model.room.entities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking

object DataConverters {
    fun convertMealPlanToDatabaseMealPlan(mealPlan: MealPlan): DatabaseMealPlan {
        return DatabaseMealPlan(
            id = mealPlan.id,
            title = mealPlan.title,
            requiredCalories = mealPlan.requiredCalories,
            requiredProtein = mealPlan.requiredProtein,
            requiredFat = mealPlan.requiredFat,
            requiredCarbohydrates = mealPlan.requiredCarbohydrates
        )
    }

    fun convertDatabaseMealPlanToMealPlan(databaseMealPlan: DatabaseMealPlan): MealPlan {
        return MealPlan(
            id = databaseMealPlan.id,
            title = databaseMealPlan.title,
            requiredCalories = databaseMealPlan.requiredCalories,
            requiredProtein = databaseMealPlan.requiredProtein,
            requiredFat = databaseMealPlan.requiredFat,
            requiredCarbohydrates = databaseMealPlan.requiredCarbohydrates
        )
    }

    fun convertMealPlanMealToDatabaseMealPlanMeal(mealPlanMeal: MealPlanMeal): DatabaseMealPlanMeal {
        return DatabaseMealPlanMeal(
            id = mealPlanMeal.id,
            mealPlanId = mealPlanMeal.mealPlanId,
            mealId = mealPlanMeal.mealId
        )
    }

    suspend fun convertDatabaseMealPlanMealToMealPlanMeal(
        databaseMealPlanMeal: DatabaseMealPlanMeal,
        mealsDao: MealsDao,
        mealFoodsDao: MealFoodsDao,
        foodsDao: FoodsDao
    ): MealPlanMeal {
        val meal = convertDatabaseMealToMeal(
            mealsDao.getMeal(databaseMealPlanMeal.mealId),
            mealFoodsDao,
            foodsDao
        )
        return MealPlanMeal(
            id = databaseMealPlanMeal.id,
            mealPlanId = databaseMealPlanMeal.mealPlanId,
            mealId = databaseMealPlanMeal.mealId,
            title = meal.title,
            foods = meal.foods
        )
    }

    fun convertMealFoodToFood(mealFood: MealFood): Food {
        return Food(
            id = 0,
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
            id = 0,
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
        return DatabaseMealFood(mealFood.id, mealFood.foodId, mealId, mealFood.desiredServingSize)
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

    suspend fun convertDatabaseMealToMeal(
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
        println("--- START Converting database meal food into meal food ---")
        println("databaseMealFood = $databaseMealFood")

        val databaseFood = foodsDao.getFood(databaseMealFood.foodId)
        println("databaseFood = $databaseFood")

        val mealFood = MealFood(
            id = databaseFood.id,
            title = databaseFood.title,
            desiredServingSize = databaseFood.servingSize,
            macroNutrients = MacroNutrients(
                calories = databaseFood.macroNutrients.calories,
                carbs = databaseFood.macroNutrients.carbs,
                fat = databaseFood.macroNutrients.fat,
                protein = databaseFood.macroNutrients.protein
            )
        )
        println("mealFood = $mealFood")

        val updatedMealFood = MacroCalculator.updateMacrosForMealFoodWithNewServingSize(mealFood, databaseMealFood.desiredServingSize)
        println("updatedMealFood = $updatedMealFood")

        println("--- END Converting database meal food into meal food ---")

        return updatedMealFood
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