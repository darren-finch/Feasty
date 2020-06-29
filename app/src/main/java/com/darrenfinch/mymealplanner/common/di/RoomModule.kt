package com.darrenfinch.mymealplanner.common.di

import android.app.Application
import androidx.room.Room
import com.darrenfinch.mymealplanner.model.FoodsRepository
import com.darrenfinch.mymealplanner.model.MealsRepository
import com.darrenfinch.mymealplanner.model.room.MealPlannerDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(application: Application) {
    private var database: MealPlannerDatabase =
        Room.databaseBuilder(
            application.applicationContext,
            MealPlannerDatabase::class.java,
            "MealPlannerDatabase"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideDatabase(): MealPlannerDatabase {
        return database
    }

    @Provides
    @Singleton
    fun provideMealsRepository(): MealsRepository {
        return MealsRepository(provideDatabase())
    }

    @Provides
    @Singleton
    fun provideFoodsRepository(): FoodsRepository {
        return FoodsRepository(provideDatabase())
    }
}