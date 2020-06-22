package com.darrenfinch.mymealplanner.model

import com.darrenfinch.mymealplanner.model.room.DatabaseMeal
import com.darrenfinch.mymealplanner.model.room.Meal
import com.darrenfinch.mymealplanner.model.room.MealPlannerDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class MealsRepository
@Inject
constructor(database: MealPlannerDatabase) {
    private val mealsDao = database.mealsDao()

    suspend fun getMeals(): List<Meal> {
        val allDatabaseMeals = mealsDao.getMeals()
        return allDatabaseMeals.parallelMap {
            convertDatabaseMealToRegularMeal(it)
        }
    }
    private fun <A, B> List<A>.parallelMap(f: suspend (A) -> B): List<B> = runBlocking {
        map { async(Dispatchers.IO) { f(it) } }.awaitAll()
    }
    private suspend fun convertDatabaseMealToRegularMeal(databaseMeal: DatabaseMeal): Meal {
        return Meal(databaseMeal.id, databaseMeal.title, mealsDao.getMealFoodsWithIds(databaseMeal.foodsIds))
    }
    private fun convertMealToDatabaseMeal(meal: Meal): DatabaseMeal {
        return DatabaseMeal(meal.id, meal.title, meal.foods.map { it.id })
    }

    fun insertMeal(meal: Meal) {
        runBlocking(Dispatchers.IO) {
            mealsDao.insertMeal(convertMealToDatabaseMeal(meal))
        }
    }

    fun updateMeal(meal: Meal) {
        runBlocking(Dispatchers.IO) {
            mealsDao.updateMeal(convertMealToDatabaseMeal(meal))
        }
    }

    fun deleteMeal(meal: Meal) {
        runBlocking(Dispatchers.IO) {
            mealsDao.deleteMeal(convertMealToDatabaseMeal(meal))
        }
    }
}