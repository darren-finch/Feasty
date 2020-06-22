package com.darrenfinch.mymealplanner.di

import android.app.Application
import androidx.room.Room
import com.darrenfinch.mymealplanner.model.FoodsRepository
import com.darrenfinch.mymealplanner.model.MealsRepository
import com.darrenfinch.mymealplanner.model.room.MealPlannerDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(application: Application)
{
    private var database: MealPlannerDatabase =
        Room.databaseBuilder(application.applicationContext, MealPlannerDatabase::class.java, "MealPlannerDatabase")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideDatabase() : MealPlannerDatabase
    {
        return database
    }
    @Provides
    @Singleton
    fun provideMealsRepository(database: MealPlannerDatabase) : MealsRepository
    {
        return MealsRepository(database)
    }
    @Provides
    @Singleton
    fun provideFoodsRepository(database: MealPlannerDatabase) : FoodsRepository
    {
        return FoodsRepository(database)
    }
}