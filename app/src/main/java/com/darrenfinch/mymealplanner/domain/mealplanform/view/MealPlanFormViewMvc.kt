package com.darrenfinch.mymealplanner.domain.mealplanform.view

import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.domain.viewmodels.ObservableMealPlan
import com.darrenfinch.mymealplanner.model.data.entities.MealPlan

interface MealPlanFormViewMvc : ObservableViewMvc<MealPlanFormViewMvc.Listener> {
    interface Listener {
        fun onDoneClicked(finalMealPlan: MealPlan)
    }
    fun bindMealPlan(mealPlan: ObservableMealPlan)
}