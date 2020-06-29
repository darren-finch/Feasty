package com.darrenfinch.mymealplanner.common.di

import android.app.Application
import androidx.room.Room
import com.darrenfinch.mymealplanner.model.FoodsRepository
import com.darrenfinch.mymealplanner.model.MealsRepository
import com.darrenfinch.mymealplanner.model.room.MealPlannerDatabase

//This is the global composition root
class CompositionRoot(private val application: Application) {
    private var database: MealPlannerDatabase =
        Room.databaseBuilder(application.applicationContext, MealPlannerDatabase::class.java, "MealPlannerDatabase")
            .fallbackToDestructiveMigration()
            .build()

    fun getApplication(): Application {
        return application
    }

    private fun getDatabase(): MealPlannerDatabase {
        return database
    }

    fun getMealsRepository(): MealsRepository {
        return MealsRepository(getDatabase())
    }

    fun getFoodsRepository(): FoodsRepository {
        return FoodsRepository(getDatabase())
    }
}