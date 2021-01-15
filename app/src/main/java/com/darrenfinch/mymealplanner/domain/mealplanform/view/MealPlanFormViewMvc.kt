package com.darrenfinch.mymealplanner.domain.mealplanform.view

import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.model.data.entities.MealPlan

interface MealPlanFormViewMvc : ObservableViewMvc<MealPlanFormViewMvc.Listener> {
    interface Listener {
        fun onDoneButtonClicked(editedMealPlanDetails: MealPlan)
    }

    fun bindMealPlanDetails(mealPlan: MealPlan)
    fun getMealPlanDetails(): MealPlan
}