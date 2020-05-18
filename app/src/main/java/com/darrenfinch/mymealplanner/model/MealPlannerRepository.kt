package com.darrenfinch.mymealplanner.model

import com.darrenfinch.mymealplanner.model.room.DatabaseMeal
import com.darrenfinch.mymealplanner.model.room.Meal
import com.darrenfinch.mymealplanner.model.room.MealPlannerDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class MealPlannerRepository
@Inject
constructor(database: MealPlannerDatabase)
{
//    companion object
//    {
//        private var INSTANCE: MealPlannerRepository? = null
//
//        fun getInstance(database: MealPlannerDatabase) : MealPlannerRepository
//        {
//            return if(INSTANCE == null)
//            {
//                INSTANCE = MealPlannerRepository()
//                INSTANCE!!
//            }
//            else
//                INSTANCE!!
//        }
//    }

    private val mealsDao = database.mealsDao()
    private val foodsDao = database.foodsDao()

    suspend fun getMeals() : List<Meal>
    {
        val allDatabaseMeals = mealsDao.getMeals()
        return allDatabaseMeals.pmap {
            convertDatabaseMealToRegularMeal(it)
        }
    }
    private suspend fun convertDatabaseMealToRegularMeal(databaseMeal: DatabaseMeal) : Meal
    {
        return Meal(databaseMeal.title, foodsDao.getFoodsWithIds(databaseMeal.foodsIds))
    }
    private fun <A, B>List<A>.pmap(f: suspend (A) -> B): List<B> = runBlocking {
        map { async(Dispatchers.IO) { f(it) } }.map { it.await() }
    }

    fun insertMeal(meal: DatabaseMeal)
    {
        runBlocking(Dispatchers.IO){
            mealsDao.insertMeal(meal)
        }
    }
}