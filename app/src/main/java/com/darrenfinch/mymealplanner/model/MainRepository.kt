package com.darrenfinch.mymealplanner.model

import com.darrenfinch.mymealplanner.model.room.FeastyDatabase
import com.darrenfinch.mymealplanner.model.room.models.foods.DatabaseFood
import com.darrenfinch.mymealplanner.model.room.models.mealplans.DatabaseMealPlan
import com.darrenfinch.mymealplanner.model.room.models.mealplans.DatabaseMealPlanMeal
import com.darrenfinch.mymealplanner.model.room.models.meals.DatabaseMeal
import com.darrenfinch.mymealplanner.model.room.models.meals.DatabaseMealFood

/**
 * This class primarily exists as a single source of truth, and manages DAOs and network services.
 * Use cases then access this repository and do the conversion from data models to ui models.
 * Uses cases also perform all business logic.
 */
class MainRepository(database: FeastyDatabase) {
    private val mealsDao = database.mealsDao()
    private val foodsDao = database.foodsDao()
    private val mealFoodsDao = database.mealFoodsDao()
    private val mealPlansDao = database.mealPlansDao()
    private val mealPlanMealsDao = database.mealPlanMealsDao()

    // Meal Plans

    suspend fun getAllMealPlans() = mealPlansDao.getAllMealPlans()
    suspend fun getMealPlan(id: Int) = mealPlansDao.getMealPlan(id)
    suspend fun insertMealPlan(mealPlan: DatabaseMealPlan) = mealPlansDao.insertMealPlan(mealPlan)
    suspend fun updateMealPlan(mealPlan: DatabaseMealPlan) = mealPlansDao.updateMealPlan(mealPlan)
    suspend fun deleteMealPlan(id: Int) = mealPlansDao.deleteMealPlan(id)

    // Meal Plan Meals

    suspend fun getMealsForMealPlan(mealPlanId: Int) = mealPlanMealsDao.getMealsForMealPlan(mealPlanId)
    suspend fun getMealPlanMeal(id: Int) = mealPlanMealsDao.getMealPlanMeal(id)
    suspend fun insertMealPlanMeal(mealPlanMeal: DatabaseMealPlanMeal) = mealPlanMealsDao.insertMealPlanMeal(mealPlanMeal)
    suspend fun updateMealPlanMeal(mealPlanMeal: DatabaseMealPlanMeal) = mealPlanMealsDao.updateMealPlanMeal(mealPlanMeal)
    suspend fun deleteMealPlanMeal(id: Int) = mealPlanMealsDao.deleteMealPlanMeal(id)

    // Meals

    suspend fun getAllMeals() = mealsDao.getAllMeals()
    suspend fun getMeal(id: Int) = mealsDao.getMeal(id)
    suspend fun insertMeal(meal: DatabaseMeal) = mealsDao.insertMeal(meal)
    suspend fun updateMeal(meal: DatabaseMeal) = mealsDao.updateMeal(meal)
    suspend fun deleteMeal(id: Int) = mealsDao.deleteMeal(id)

    // Meal Foods

    suspend fun getMealFoodsForMeal(mealId: Int) = mealFoodsDao.getMealFoodsForMeal(mealId)
    suspend fun getMealFoodsForFood(foodId: Int) = mealFoodsDao.getMealFoodsForMeal(foodId)
    suspend fun insertMealFood(mealFood: DatabaseMealFood) = mealFoodsDao.insertMealFood(mealFood)
    suspend fun updateMealFood(mealFood: DatabaseMealFood) = mealFoodsDao.updateMealFood(mealFood)
    suspend fun deleteMealFood(id: Int) = mealFoodsDao.deleteMealFood(id)

    // Foods

    suspend fun getAllFoods() = foodsDao.getAllFoods()
    suspend fun getFood(id: Int) = foodsDao.getFood(id)
    suspend fun insertFood(food: DatabaseFood) = foodsDao.insertFood(food)
    suspend fun updateFood(food: DatabaseFood) = foodsDao.updateFood(food)
    suspend fun deleteFood(id: Int) = foodsDao.deleteFood(id)
}