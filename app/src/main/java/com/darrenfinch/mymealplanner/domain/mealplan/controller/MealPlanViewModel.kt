package com.darrenfinch.mymealplanner.domain.mealplan.controller

import androidx.lifecycle.ViewModel
import com.darrenfinch.mymealplanner.model.data.entities.MealPlan

class MealPlanViewModel : ViewModel() {
    var selectedMealPlanIndex = 0
    var selectedMealPlan: MealPlan? = null
    var allMealPlans: List<MealPlan> = listOf()

    fun hasPrevMealPlanIndex() = selectedMealPlanIndex - 1 >= 0
    fun hasNextMealPlanIndex() = selectedMealPlanIndex + 1 <= allMealPlans.lastIndex
}