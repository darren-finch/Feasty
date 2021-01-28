package com.darrenfinch.mymealplanner.screens.allmeals.view

import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.common.views.ProgressIndicatorViewMvc
import com.darrenfinch.mymealplanner.meals.models.domain.Meal
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal

interface AllMealsViewMvc : ObservableViewMvc<AllMealsViewMvc.Listener>, ProgressIndicatorViewMvc {
    interface Listener {
        fun onAddNewMealClicked()
        fun onMealEdit(mealId: Int)
        fun onMealDelete(mealId: Int)
    }

    fun bindMeals(newMeals: List<UiMeal>)
}