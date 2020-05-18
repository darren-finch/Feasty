package com.darrenfinch.mymealplanner.di

import android.app.Application
import com.darrenfinch.mymealplanner.model.MealPlannerRepository
import com.darrenfinch.mymealplanner.model.room.MealPlannerDatabase
import com.darrenfinch.mymealplanner.viewmodels.AllMealsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RoomModule::class])
interface ApplicationComponent
{
    fun inject(mealPlannerViewModel: AllMealsViewModel)
    fun mealPlannerRepository() : MealPlannerRepository
    fun mealPlannerDatabase() : MealPlannerDatabase
    fun application() : Application
}