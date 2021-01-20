package com.darrenfinch.mymealplanner.screens.mealform.view

import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.meals.models.Meal

interface MealFormViewMvc : ObservableViewMvc<MealFormViewMvc.Listener> {
    interface Listener {
        fun onAddNewFoodButtonClicked()
        fun onDoneButtonClicked(editedMealDetails: Meal)
    }

    fun bindMealDetails(mealDetails: Meal)
    fun getMealDetails(): Meal
}