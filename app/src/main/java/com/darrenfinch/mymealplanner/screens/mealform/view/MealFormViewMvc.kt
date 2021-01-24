package com.darrenfinch.mymealplanner.screens.mealform.view

import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.common.views.ProgressIndicatorViewMvc
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal

interface MealFormViewMvc : ObservableViewMvc<MealFormViewMvc.Listener>, ProgressIndicatorViewMvc {
    interface Listener {
        fun onAddNewFoodButtonClicked()
        fun onDoneButtonClicked(editedMealDetails: UiMeal)
        fun onNavigateUp()
    }

    fun bindMealDetails(mealDetails: UiMeal)
    fun getMealDetails(): UiMeal
}