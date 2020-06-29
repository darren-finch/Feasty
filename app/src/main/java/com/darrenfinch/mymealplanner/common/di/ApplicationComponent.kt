package com.darrenfinch.mymealplanner.common.di

import android.app.Application
import com.darrenfinch.mymealplanner.domain.allfoods.controller.AllFoodsViewModel
import com.darrenfinch.mymealplanner.domain.allmeals.controller.AllMealsViewModel
import com.darrenfinch.mymealplanner.model.FoodsRepository
import com.darrenfinch.mymealplanner.model.MealsRepository
import com.darrenfinch.mymealplanner.model.room.MealPlannerDatabase
import com.darrenfinch.mymealplanner.domain.selectfoodformeal.controller.SelectFoodForMealViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RoomModule::class])
interface ApplicationComponent
{
    fun inject(viewModel: AllFoodsViewModel)
    fun inject(viewModel: SelectFoodForMealViewModel)
    fun inject(viewModel: AllMealsViewModel)
    fun mealsRepository() : MealsRepository
    fun foodsRepository() : FoodsRepository
    fun mealPlannerDatabase() : MealPlannerDatabase
    fun application() : Application
}