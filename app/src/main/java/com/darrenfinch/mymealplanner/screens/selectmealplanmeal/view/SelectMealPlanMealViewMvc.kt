package com.darrenfinch.mymealplanner.screens.selectmealplanmeal.view

import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal

interface SelectMealPlanMealViewMvc : ObservableViewMvc<SelectMealPlanMealViewMvc.Listener> {
    interface Listener {
        fun onNavigateUp()
        fun onMealChosen(chosenMeal: UiMeal)
    }

    fun bindMeals(meals: List<UiMeal>)
}