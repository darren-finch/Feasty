package com.darrenfinch.mymealplanner.common.dependencyinjection

import android.app.Application
import androidx.room.Room
import com.darrenfinch.mymealplanner.model.MainRepository
import com.darrenfinch.mymealplanner.model.room.MealPlannerDatabase

//This is the global composition root
class CompositionRoot(private val application: Application) {
    private val database: MealPlannerDatabase =
        Room.databaseBuilder(application.applicationContext, MealPlannerDatabase::class.java, "MealPlannerDatabase")
            .fallbackToDestructiveMigration()
            .build()

    private val mainRepository = MainRepository(getDatabase())

    fun getApplication(): Application {
        return application
    }

    private fun getDatabase(): MealPlannerDatabase {
        return database
    }

    fun getMainRepository(): MainRepository {
        return mainRepository
    }
}