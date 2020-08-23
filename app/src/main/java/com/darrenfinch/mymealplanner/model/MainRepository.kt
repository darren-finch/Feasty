package com.darrenfinch.mymealplanner.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.darrenfinch.mymealplanner.model.DataConverters.convertDatabaseFoodToFood
import com.darrenfinch.mymealplanner.model.DataConverters.convertDatabaseMealToRegularMeal
import com.darrenfinch.mymealplanner.model.DataConverters.convertFoodToDatabaseFood
import com.darrenfinch.mymealplanner.model.DataConverters.convertMealFoodToDatabaseMealFood
import com.darrenfinch.mymealplanner.model.DataConverters.convertMealToDatabaseMeal
import com.darrenfinch.mymealplanner.model.data.entities.Food
import com.darrenfinch.mymealplanner.model.data.entities.Meal
import com.darrenfinch.mymealplanner.model.room.MealPlannerDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking

class MainRepository constructor(database: MealPlannerDatabase) {
    private val mealsDao = database.mealsDao()
    private val foodsDao = database.foodsDao()
    private val mealFoodsDao = database.mealFoodsDao()

    private val allMeals: LiveData<List<Meal>>
        get() {
            return Transformations.map(mealsDao.getMeals()) {
                it.parallelMap { databaseMeal ->
                    convertDatabaseMealToRegularMeal(databaseMeal, mealFoodsDao, foodsDao)
                }
            }
        }

    private val allFoods: LiveData<List<Food>>
        get() {
            return Transformations.map(foodsDao.getFoods()) {
                it.parallelMap { databaseFood ->
                    convertDatabaseFoodToFood(databaseFood)
                }
            }
        }


    fun getFoods(): LiveData<List<Food>> {
        return allFoods
    }

    fun getFood(foodId: Int): LiveData<Food> {
        val currentFood = MutableLiveData<Food>()
        runBlocking(Dispatchers.IO) {
            currentFood.postValue(convertDatabaseFoodToFood(foodsDao.getFood(foodId)))
        }
        return currentFood
    }

    fun insertFood(food: Food) {
        runBlocking(Dispatchers.IO) {
            foodsDao.insertFood(convertFoodToDatabaseFood(food))
        }
    }

    fun updateFood(food: Food) {
        runBlocking(Dispatchers.IO) {
            foodsDao.updateFood(convertFoodToDatabaseFood(food))
        }
    }

    fun deleteFood(foodId: Int) {
        runBlocking(Dispatchers.IO) {
            foodsDao.deleteFood(foodId)
        }
    }

    fun getMeals(): LiveData<List<Meal>> {
        return allMeals
    }

    fun getMeal(mealId: Int): LiveData<Meal> {
        val currentMeal = MutableLiveData<Meal>()
        runBlocking(Dispatchers.IO) {
            currentMeal.postValue(
                convertDatabaseMealToRegularMeal(
                    mealsDao.getMeal(mealId).value!!,
                    mealFoodsDao,
                    foodsDao
                )
            )
        }
        return currentMeal
    }

    fun insertMeal(meal: Meal) {
        runBlocking(Dispatchers.IO) {
            val newMealId = mealsDao.insertMeal(convertMealToDatabaseMeal(meal))
            for (mealFood in meal.foods) {
                mealFoodsDao.insertMealFood(
                    convertMealFoodToDatabaseMealFood(
                        mealFood,
                        newMealId.toInt()
                    )
                )
            }
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