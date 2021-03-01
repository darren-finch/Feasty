package com.darrenfinch.mymealplanner.data

import com.darrenfinch.mymealplanner.data.room.FeastyDatabase
import com.darrenfinch.mymealplanner.data.room.models.foods.DbFood
import com.darrenfinch.mymealplanner.data.room.models.mealplans.DbMealPlan
import com.darrenfinch.mymealplanner.data.room.models.mealplans.DbMealPlanMeal
import com.darrenfinch.mymealplanner.data.room.models.meals.DbMeal
import com.darrenfinch.mymealplanner.data.room.models.meals.DbMealFood

/**
 * This class primarily exists as a single source of truth, and manages DAOs and network services.
 * Use cases then access this repository and do the conversion from data models to ui models.
 * Uses cases also perform all business logic.
 */
class MainRepository(database: FeastyDatabase) {
    private val mealPlansDao = database.mealPlansDao()
    private val mealPlanMealsDao = database.mealPlanMealsDao()
    private val mealsDao = database.mealsDao()
    private val mealFoodsDao = database.mealFoodsDao()
    private val foodsDao = database.foodsDao()

    // Meal Plans

    suspend fun getAllMealPlans() = mealPlansDao.getAllMealPlans()
    suspend fun getMealPlan(id: Int) = mealPlansDao.getMealPlan(id)
    suspend fun insertMealPlan(mealPlan: DbMealPlan) = mealPlansDao.insertMealPlan(mealPlan)
    suspend fun updateMealPlan(mealPlan: DbMealPlan) = mealPlansDao.updateMealPlan(mealPlan)
    suspend fun deleteMealPlan(id: Int) = mealPlansDao.deleteMealPlan(id)

    // Meal Plan Meals

    suspend fun getMealsForMealPlan(mealPlanId: Int) = mealPlanMealsDao.getMealsForMealPlan(mealPlanId)
    suspend fun getMealPlanMeal(id: Int) = mealPlanMealsDao.getMealPlanMeal(id)
    suspend fun insertMealPlanMeal(mealPlanMeal: DbMealPlanMeal) = mealPlanMealsDao.insertMealPlanMeal(mealPlanMeal)
    suspend fun updateMealPlanMeal(mealPlanMeal: DbMealPlanMeal) = mealPlanMealsDao.updateMealPlanMeal(mealPlanMeal)
    suspend fun deleteMealPlanMeal(id: Int) = mealPlanMealsDao.deleteMealPlanMeal(id)

    // Meals

    suspend fun getAllMeals() = mealsDao.getAllMeals()
    suspend fun getMealsForQuery(query: String) = mealsDao.getMealsForQuery(query)
    suspend fun getMeal(id: Int) = mealsDao.getMeal(id)
    suspend fun insertMeal(meal: DbMeal) = mealsDao.insertMeal(meal)
    suspend fun updateMeal(meal: DbMeal) = mealsDao.updateMeal(meal)
    suspend fun deleteMeal(id: Int) = mealsDao.deleteMeal(id)

    // Meal Foods
    suspend fun getMealFoodsForMeal(mealId: Int) = mealFoodsDao.getMealFoodsForMeal(mealId)
    suspend fun getMealFoodsForFood(foodId: Int) = mealFoodsDao.getMealFoodsForMeal(foodId)
    suspend fun getMealFood(id: Int) = mealFoodsDao.getMealFood(id)
    suspend fun insertMealFood(mealFood: DbMealFood) = mealFoodsDao.insertMealFood(mealFood)
    suspend fun updateMealFood(mealFood: DbMealFood) = mealFoodsDao.updateMealFood(mealFood)
    suspend fun deleteMealFood(id: Int) = mealFoodsDao.deleteMealFood(id)

    // Foods
    suspend fun getAllFoods() = foodsDao.getAllFoods()
    suspend fun getFoodsFromQuery(query: String) = foodsDao.getFoodsFromQuery(query)
    suspend fun getFood(id: Int) = foodsDao.getFood(id)
    suspend fun insertFood(food: DbFood) = foodsDao.insertFood(food)
    suspend fun updateFood(food: DbFood) = foodsDao.updateFood(food)
    suspend fun deleteFood(id: Int) = foodsDao.deleteFood(id)
}