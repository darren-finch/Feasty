package com.darrenfinch.mymealplanner.model

import com.darrenfinch.mymealplanner.foods.models.Food
import com.darrenfinch.mymealplanner.mealplans.models.MealPlan
import com.darrenfinch.mymealplanner.mealplans.models.MealPlanMeal
import com.darrenfinch.mymealplanner.meals.models.Meal
import com.darrenfinch.mymealplanner.meals.models.MealFood
import com.darrenfinch.mymealplanner.foods.models.MacroNutrients
import com.darrenfinch.mymealplanner.foods.models.MacroCalculator
import com.darrenfinch.mymealplanner.model.room.daos.FoodsDao
import com.darrenfinch.mymealplanner.model.room.daos.MealFoodsDao
import com.darrenfinch.mymealplanner.model.room.daos.MealsDao
import com.darrenfinch.mymealplanner.model.room.entities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking

object ModelConverters {
    fun convertMealPlanToDatabaseMealPlan(mealPlan: MealPlan): DatabaseMealPlan {
        return DatabaseMealPlan(
            id = mealPlan.id,
            title = mealPlan.title,
            requiredCalories = mealPlan.requiredCalories,
            requiredProtein = mealPlan.requiredProteins,
            requiredFat = mealPlan.requiredFats,
            requiredCarbohydrates = mealPlan.requiredCarbohydrates
        )
    }

    fun convertDatabaseMealPlanToMealPlan(databaseMealPlan: DatabaseMealPlan): MealPlan {
        return MealPlan(
            id = databaseMealPlan.id,
            title = databaseMealPlan.title,
            requiredCalories = databaseMealPlan.requiredCalories,
            requiredProteins = databaseMealPlan.requiredProtein,
            requiredFats = databaseMealPlan.requiredFat,
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
                fats = mealFood.macroNutrients.fats,
                proteins = mealFood.macroNutrients.proteins
            )
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
                mealFoodsDao.getMealFoodsFromMealId(databaseMeal.id),
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
        }.filterNotNull()
    }

    // TODO: REMOVE NULLABILITY. ADDED TO FIX A BUG
    suspend fun convertDatabaseMealFoodToMealFood(
        databaseMealFood: DatabaseMealFood,
        foodsDao: FoodsDao
    ): MealFood? {
        val databaseFood: DatabaseFood? = foodsDao.getFood(databaseMealFood.foodId)
        databaseFood?.let {
            val mealFood = MealFood(
                id = it.id,
                foodId = databaseMealFood.foodId,
                mealId = databaseMealFood.mealId,
                title = databaseFood.title,
                desiredServingSize = databaseFood.servingSize,
                macroNutrients = MacroNutrients(
                    calories = databaseFood.macroNutrients.calories,
                    carbs = databaseFood.macroNutrients.carbs,
                    fats = databaseFood.macroNutrients.fats,
                    proteins = databaseFood.macroNutrients.proteins
                )
            )
            return MacroCalculator.updateMacrosForMealFoodWithNewServingSize(mealFood, databaseMealFood.desiredServingSize)
        }
        return null
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