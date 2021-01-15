package com.darrenfinch.mymealplanner.domain.mealform.view

import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.model.data.entities.Meal

interface MealFormViewMvc : ObservableViewMvc<MealFormViewMvc.Listener> {
    interface Listener {
        fun onAddNewFoodButtonClicked()
        fun onDoneButtonClicked(editedMealDetails: Meal)
    }

    fun bindMealDetails(mealDetails: Meal)
    fun getMealDetails(): Meal
}