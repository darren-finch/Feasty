package com.darrenfinch.mymealplanner.di

import android.app.Application
import com.darrenfinch.mymealplanner.model.FoodsRepository
import com.darrenfinch.mymealplanner.model.MealsRepository
import com.darrenfinch.mymealplanner.model.room.MealPlannerDatabase
import com.darrenfinch.mymealplanner.viewmodels.AddEditFoodViewModel
import com.darrenfinch.mymealplanner.viewmodels.AddEditMealViewModel
import com.darrenfinch.mymealplanner.viewmodels.AllFoodsViewModel
import com.darrenfinch.mymealplanner.viewmodels.AllMealsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RoomModule::class])
interface ApplicationComponent
{
    fun inject(viewModel: AllFoodsViewModel)
    fun inject(viewModel: AddEditFoodViewModel)
    fun inject(viewModel: AddEditMealViewModel)
    fun inject(viewModel: AllMealsViewModel)
    fun mealsRepository() : MealsRepository
    fun foodsRepository() : FoodsRepository
    fun mealPlannerDatabase() : MealPlannerDatabase
    fun application() : Application
}