package com.darrenfinch.mymealplanner.domain.mealform.view

import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.domain.viewmodels.ObservableMeal
import com.darrenfinch.mymealplanner.model.data.entities.Meal

interface MealFormViewMvc : ObservableViewMvc<MealFormViewMvc.Listener> {
    interface Listener {
        fun addNewFoodButtonClicked()
        fun doneButtonClicked()
    }

    fun bindMealDetails(mealDetails: Meal)
}