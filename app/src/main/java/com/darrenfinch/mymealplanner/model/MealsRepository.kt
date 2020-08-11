package com.darrenfinch.mymealplanner.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.darrenfinch.mymealplanner.model.data.DataConverters.convertDatabaseMealToRegularMeal
import com.darrenfinch.mymealplanner.model.data.DataConverters.convertMealToDatabaseMeal
import com.darrenfinch.mymealplanner.model.data.DatabaseMeal
import com.darrenfinch.mymealplanner.model.data.Meal
import com.darrenfinch.mymealplanner.model.room.MealPlannerDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking

class MealsRepository constructor(database: MealPlannerDatabase) {
    private val mealsDao = database.mealsDao()
    private val foodsDao = database.foodsDao() //Minimize usage of this. Right now, there isn't a good way to get around needing this.

    private val allDatabaseMeals: LiveData<List<DatabaseMeal>> = mealsDao.getMeals()
    private val allMeals = Transformations.map(allDatabaseMeals) {
        allDatabaseMeals.value!!.parallelMap { databaseMeal ->
            convertDatabaseMealToRegularMeal(databaseMeal, foodsDao)
        }
    }

    private val currentMeal = MutableLiveData<Meal>()

    fun getMeals(): LiveData<List<Meal>> {
        return allMeals
    }
    fun getMeal(mealId: Int): LiveData<Meal> {
        runBlocking(Dispatchers.IO) {
            currentMeal.postValue(convertDatabaseMealToRegularMeal(mealsDao.getMeal(mealId).value!!, foodsDao))
        }
        return currentMeal
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
        map { async(Dispatchers.Default) { f(it) } }.awaitAll()
    }

    fun deleteAllMeals() {
        runBlocking(Dispatchers.IO) {
            mealsDao.deleteAllMeals()
        }
    }
}