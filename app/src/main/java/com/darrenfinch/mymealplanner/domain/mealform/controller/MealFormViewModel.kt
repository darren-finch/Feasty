package com.darrenfinch.mymealplanner.domain.mealform.controller

import androidx.lifecycle.ViewModel
import com.darrenfinch.mymealplanner.domain.common.ObservableMeal
import com.darrenfinch.mymealplanner.model.data.entities.Meal

class MealFormViewModel : ViewModel() {
    private val observableMeal = ObservableMeal()
    fun getObservableMeal() = observableMeal
    fun setObservableMeal(meal: Meal) {
        observableMeal.set(meal)
    }
}