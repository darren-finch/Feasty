package com.darrenfinch.mymealplanner.domain.foodform.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.darrenfinch.mymealplanner.domain.observables.ObservableFood
import com.darrenfinch.mymealplanner.model.data.entities.Food

class FoodFormViewModel(val foodId: Int, application: Application) : AndroidViewModel(application) {

    val insertingFood = foodId < 0

    private val observableFood = ObservableFood()
    fun getObservableFood() = observableFood
    fun setObservableFoodData(foodData: Food) = observableFood.set(foodData)

    fun isDirty() = observableFood.dirty
    fun isNotDirty() = !isDirty()
}