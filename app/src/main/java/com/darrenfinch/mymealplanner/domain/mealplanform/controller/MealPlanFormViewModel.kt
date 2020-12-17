package com.darrenfinch.mymealplanner.domain.mealplanform.controller

import androidx.lifecycle.ViewModel
import com.darrenfinch.mymealplanner.domain.observables.ObservableMealPlan

class MealPlanFormViewModel : ViewModel() {
    val observableMealPlan = ObservableMealPlan()
}