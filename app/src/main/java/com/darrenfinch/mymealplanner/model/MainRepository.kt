package com.darrenfinch.mymealplanner.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.darrenfinch.mymealplanner.model.DataConverters.convertDatabaseFoodToFood
import com.darrenfinch.mymealplanner.model.DataConverters.convertDatabaseMealPlanMealToMealPlanMeal
import com.darrenfinch.mymealplanner.model.DataConverters.convertDatabaseMealPlanToMealPlan
import com.darrenfinch.mymealplanner.model.DataConverters.convertDatabaseMealToRegularMeal
import com.darrenfinch.mymealplanner.model.DataConverters.convertFoodToDatabaseFood
import com.darrenfinch.mymealplanner.model.DataConverters.convertMealFoodToDatabaseMealFood
import com.darrenfinch.mymealplanner.model.DataConverters.convertMealToDatabaseMeal
import com.darrenfinch.mymealplanner.model.data.entities.Food
import com.darrenfinch.mymealplanner.model.data.entities.Meal
import com.darrenfinch.mymealplanner.model.data.entities.MealPlan
import com.darrenfinch.mymealplanner.model.data.entities.MealPlanMeal
import com.darrenfinch.mymealplanner.model.room.MealPlannerDatabase
import kotlinx.coroutines.*

class MainRepository constructor(database: MealPlannerDatabase) {
    private val mealsDao = database.mealsDao()
    private val foodsDao = database.foodsDao()
    private val mealFoodsDao = database.mealFoodsDao()
    private val mealPlansDao = database.mealPlansDao()
    private val mealPlanMealsDao = database.mealPlanMealsDao()

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

    fun getMeal(id: Int): LiveData<Meal> {
        val currentMeal = MutableLiveData<Meal>()
        runBlocking(Dispatchers.IO) {
            val databaseMeal = mealsDao.getMeal(id)
            currentMeal.postValue(
                convertDatabaseMealToRegularMeal(
                    databaseMeal,
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

    fun deleteMeal(id: Int) {
        runBlocking(Dispatchers.IO) {
            mealsDao.deleteMeal(id)
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

    fun getMealPlan(id: Int): LiveData<MealPlan> {
        val mealPlanLiveData = MutableLiveData<MealPlan>()
        runBlocking(Dispatchers.IO) {
            val databaseMealPlan = convertDatabaseMealPlanToMealPlan(mealPlansDao.getMealPlan(id), mealPlanMealsDao, mealsDao, foodsDao, mealFoodsDao)
            mealPlanLiveData.postValue(databaseMealPlan)
        }
        return mealPlanLiveData
    }

    fun insertMealPlan(mealPlan: MealPlan) {
        runBlocking(Dispatchers.IO) {
            mealPlansDao.insertMealPlan(mealPlan)
        }
    }

    fun updateMealPlan(mealPlan: MealPlan) {
        runBlocking(Dispatchers.IO) {
            mealPlansDao.updateMealPlan(mealPlan)
        }
    }

    fun deleteMealPlan(mealPlan: MealPlan) {
        runBlocking(Dispatchers.IO) {
            mealPlansDao.deleteMealPlan(mealPlan)
        }
    }

    fun getMealsForMealPlan(mealPlanId: Int): LiveData<List<MealPlanMeal>> {
        return Transformations.map(mealPlanMealsDao.getMealsForMealPlan(mealPlanId)) {
            it.parallelMap { databaseMealPlanMeal ->
                convertDatabaseMealPlanMealToMealPlanMeal(databaseMealPlanMeal)
            }
        }
    }

    fun insertMealPlanMeal(mealPlanMeal: MealPlanMeal) {
        runBlocking(Dispatchers.IO) {
            mealPlanMealsDao.insertMealPlanMeal(mealPlanMeal)
        }
    }

    fun updateMealPlanMeal(mealPlanMeal: MealPlanMeal) {
        runBlocking(Dispatchers.IO) {
            mealPlanMealsDao.updateMealPlanMeal(mealPlanMeal)
        }
    }

    fun deleteMealPlanMeal(mealPlanMeal: MealPlanMeal) {
        runBlocking(Dispatchers.IO) {
            mealPlanMealsDao.deleteMealPlanMeal(mealPlanMeal)
        }
    }

    fun getMealPlanMeal(id: Int): LiveData<MealPlanMeal> {
        val mealPlanMealLiveData = MutableLiveData<MealPlanMeal>()
        runBlocking(Dispatchers.IO) {
            val databaseMealPlanMeal = mealPlanMealsDao.getMealPlanMeal(id)
            mealPlanMealLiveData.postValue(convertDatabaseMealPlanMealToMealPlanMeal(databaseMealPlanMeal))
        }
        return mealPlanMealLiveData
    }
}