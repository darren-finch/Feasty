package com.darrenfinch.mymealplanner.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.darrenfinch.mymealplanner.data.room.daos.*
import com.darrenfinch.mymealplanner.data.room.models.foods.DbFood
import com.darrenfinch.mymealplanner.data.room.models.mealplans.DbMealPlan
import com.darrenfinch.mymealplanner.data.room.models.mealplans.DbMealPlanMeal
import com.darrenfinch.mymealplanner.data.room.models.meals.DbMeal
import com.darrenfinch.mymealplanner.data.room.models.meals.DbMealFood

@Database(
    entities = [DbMeal::class, DbFood::class, DbMealFood::class, DbMealPlan::class, DbMealPlanMeal::class],
    version = 15,
    exportSchema = false
)
@TypeConverters(RoomConverters::class)
abstract class FeastyDatabase : RoomDatabase() {
    abstract fun mealsDao(): MealsDao
    abstract fun foodsDao(): FoodsDao
    abstract fun mealFoodsDao(): MealFoodsDao
    abstract fun mealPlansDao(): MealPlansDao
    abstract fun mealPlanMealsDao(): MealPlanMealsDao
}