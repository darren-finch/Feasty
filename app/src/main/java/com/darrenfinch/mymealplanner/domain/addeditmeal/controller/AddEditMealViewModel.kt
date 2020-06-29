package com.darrenfinch.mymealplanner.domain.addeditmeal.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.darrenfinch.mymealplanner.common.di.AppModule
import com.darrenfinch.mymealplanner.common.di.DaggerApplicationComponent
import com.darrenfinch.mymealplanner.common.di.RoomModule
import com.darrenfinch.mymealplanner.model.MealsRepository
import com.darrenfinch.mymealplanner.model.data.Meal
import javax.inject.Inject

class AddEditMealViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var repo: MealsRepository

    init {
        DaggerApplicationComponent.builder()
            .appModule(AppModule(application))
            .roomModule(RoomModule(application))
            .build()
            .inject(this)
    }

    fun insertMeal(meal: Meal) = repo.insertMeal(meal)
    fun updateMeal(meal: Meal) = repo.updateMeal(meal)
    fun deleteMeal(meal: Meal) = repo.deleteMeal(meal)
}