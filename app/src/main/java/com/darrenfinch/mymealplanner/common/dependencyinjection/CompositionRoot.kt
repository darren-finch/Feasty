package com.darrenfinch.mymealplanner.common.dependencyinjection

import android.app.Application
import androidx.room.Room
import com.darrenfinch.mymealplanner.model.FoodsRepository
import com.darrenfinch.mymealplanner.model.MealsRepository
import com.darrenfinch.mymealplanner.model.room.MealPlannerDatabase

//This is the global composition root
class CompositionRoot(private val application: Application) {
    private val database: MealPlannerDatabase =
        Room.databaseBuilder(application.applicationContext, MealPlannerDatabase::class.java, "MealPlannerDatabase")
            .fallbackToDestructiveMigration()
            .build()

    private val mealsRepository = MealsRepository(getDatabase())
    private val foodsRepository = FoodsRepository(getDatabase())

    fun getApplication(): Application {
        return application
    }

    private fun getDatabase(): MealPlannerDatabase {
        return database
    }

    fun getMealsRepository(): MealsRepository {
        return mealsRepository
    }

    fun getFoodsRepository(): FoodsRepository {
        return foodsRepository
    }
}