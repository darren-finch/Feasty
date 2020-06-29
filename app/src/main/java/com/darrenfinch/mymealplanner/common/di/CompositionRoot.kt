package com.darrenfinch.mymealplanner.common.di

import android.app.Application
import androidx.room.Room
import com.darrenfinch.mymealplanner.model.FoodsRepository
import com.darrenfinch.mymealplanner.model.MealsRepository
import com.darrenfinch.mymealplanner.model.room.MealPlannerDatabase

class CompositionRoot(private val application: Application) {
    private var database: MealPlannerDatabase =
        Room.databaseBuilder(
            application.applicationContext,
            MealPlannerDatabase::class.java,
            "MealPlannerDatabase"
        )
            .fallbackToDestructiveMigration()
            .build()

    fun provideApplication() : Application {
        return application
    }

    private fun provideDatabase(): MealPlannerDatabase {
        return database
    }

    fun provideMealsRepository(): MealsRepository {
        return MealsRepository(provideDatabase())
    }

    fun provideFoodsRepository(): FoodsRepository {
        return FoodsRepository(provideDatabase())
    }
}