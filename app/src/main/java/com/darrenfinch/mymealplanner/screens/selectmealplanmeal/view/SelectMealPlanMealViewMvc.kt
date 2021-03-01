package com.darrenfinch.mymealplanner.screens.selectmealplanmeal.view

import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.common.views.ProgressIndicatorViewMvc
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal

interface SelectMealPlanMealViewMvc : ObservableViewMvc<SelectMealPlanMealViewMvc.Listener>, ProgressIndicatorViewMvc {
    interface Listener {
        fun onNavigateUp()
        fun onMealChosen(chosenMeal: UiMeal)
        fun onQuerySubmitted(query: String)
    }

    fun setQuery(query: String)
    fun bindMeals(meals: List<UiMeal>)
}