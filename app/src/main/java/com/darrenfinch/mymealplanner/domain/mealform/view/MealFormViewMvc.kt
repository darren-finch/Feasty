package com.darrenfinch.mymealplanner.domain.mealform.view

import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.domain.common.ObservableMeal

interface MealFormViewMvc : ObservableViewMvc<MealFormViewMvc.Listener> {
    interface Listener {
        fun addNewFoodButtonClicked()
        fun doneButtonClicked()
    }

    fun bindMealDetails(observableMeal: ObservableMeal)
}