package com.darrenfinch.mymealplanner.model

import androidx.lifecycle.LiveData
import com.darrenfinch.mymealplanner.model.data.Food
import com.darrenfinch.mymealplanner.model.room.MealPlannerDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class FoodsRepository
@Inject
constructor(database: MealPlannerDatabase)
{
    private val foodsDao = database.foodsDao()

    fun getFoods(): LiveData<List<Food>> {
        return foodsDao.getFoods()
    }

    fun fetchFood(foodId: Int): LiveData<Food> {
        return foodsDao.getFoodObservable(foodId)
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