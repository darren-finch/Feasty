package com.darrenfinch.mymealplanner.common.dependencyinjection

import android.app.Application
import androidx.room.Room
import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.model.MainRepository
import com.darrenfinch.mymealplanner.model.room.FeastyDatabase

//This is the global composition root
class CompositionRoot(private val application: Application) {
    private val database: FeastyDatabase =
        Room.databaseBuilder(application.applicationContext, FeastyDatabase::class.java, "MealPlannerDatabase")
            .fallbackToDestructiveMigration()
            .build()
    private val mainRepository = MainRepository(getDatabase())
    private val dialogsEventBus = DialogsEventBus()

    fun getApplication(): Application {
        return application
    }

    private fun getDatabase(): FeastyDatabase {
        return database
    }

    fun getMainRepository(): MainRepository {
        return mainRepository
    }

    fun getDialogsEventBus(): DialogsEventBus {
        return dialogsEventBus
    }
}