package com.darrenfinch.mymealplanner.screens.mealplanform.view

import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.mealplans.models.MealPlan

interface MealPlanFormViewMvc : ObservableViewMvc<MealPlanFormViewMvc.Listener> {
    interface Listener {
        fun onDoneButtonClicked(editedMealPlanDetails: MealPlan)
    }

    fun bindMealPlanDetails(mealPlan: MealPlan)
    fun getMealPlanDetails(): MealPlan
}