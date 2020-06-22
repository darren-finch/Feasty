package com.darrenfinch.mymealplanner.model

import com.darrenfinch.mymealplanner.model.data.DataConverters.convertDatabaseMealToRegularMeal
import com.darrenfinch.mymealplanner.model.data.DataConverters.convertMealToDatabaseMeal
import com.darrenfinch.mymealplanner.model.data.Meal
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
    private val foodsDao = database.foodsDao() //Minimize usage of this. Right now, there isn't a good way to get around needing this.

    suspend fun getMeals(): List<Meal> {
        val allDatabaseMeals = mealsDao.getMeals()
        return allDatabaseMeals.parallelMap {
            convertDatabaseMealToRegularMeal(it, foodsDao)
        }
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

    private fun <A, B> List<A>.parallelMap(f: suspend (A) -> B): List<B> = runBlocking {
        map { async(Dispatchers.IO) { f(it) } }.awaitAll()
    }
}