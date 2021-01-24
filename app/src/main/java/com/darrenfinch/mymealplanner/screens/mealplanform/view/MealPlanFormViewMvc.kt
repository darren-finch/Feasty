package com.darrenfinch.mymealplanner.screens.mealplanform.view

import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.common.views.ProgressIndicatorViewMvc
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlan

interface MealPlanFormViewMvc : ObservableViewMvc<MealPlanFormViewMvc.Listener>, ProgressIndicatorViewMvc {
    interface Listener {
        fun onDoneButtonClicked(editedMealPlanDetails: UiMealPlan)
        fun onNavigateUp()
    }

    fun bindMealPlanDetails(mealPlan: UiMealPlan)
    fun getMealPlanDetails(): UiMealPlan
}