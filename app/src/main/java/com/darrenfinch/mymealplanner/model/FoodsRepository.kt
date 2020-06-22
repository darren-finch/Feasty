package com.darrenfinch.mymealplanner.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.darrenfinch.mymealplanner.model.room.Food
import com.darrenfinch.mymealplanner.model.room.MealPlannerDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FoodsRepository
@Inject
constructor(database: MealPlannerDatabase)
{

    private val currentFoodLiveData = MutableLiveData<Food>()

    private val foodsDao = database.foodsDao()

    suspend fun getFoods(): List<Food> {
        return foodsDao.getFoods()
    }

    fun fetchFood(foodId: Int): LiveData<Food> {
        return foodsDao.getFood(foodId)
    }

    fun insertFood(food: Food) {
        runBlocking(Dispatchers.IO) {
            foodsDao.insertFood(food)
        }
    }

    fun updateFood(food: Food) {
        runBlocking(Dispatchers.IO) {
            foodsDao.updateFood(food)
        }
    }

    fun deleteFood(foodId: Int) {
        runBlocking(Dispatchers.IO) {
            foodsDao.deleteFood(foodId)
        }
    }
}