package com.darrenfinch.mymealplanner.domain.addeditfood.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.darrenfinch.mymealplanner.domain.common.ObservableFood
import com.darrenfinch.mymealplanner.model.data.Food

class AddEditFoodViewModel(val foodId: Int, application: Application) : AndroidViewModel(application) {

    val insertingFood = foodId < 0

    private val observableFood = ObservableFood()
    fun getObservableFood() = observableFood
    fun setObservableFoodData(foodData: Food) = observableFood.set(foodData)

    fun isDirty() = insertingFood && observableFood.dirty
    fun isNotDirty() = !isDirty()
}