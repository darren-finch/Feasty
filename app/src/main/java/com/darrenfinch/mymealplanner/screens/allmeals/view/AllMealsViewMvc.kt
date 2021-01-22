package com.darrenfinch.mymealplanner.screens.allmeals.view

import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.meals.models.domain.Meal
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal

interface AllMealsViewMvc : ObservableViewMvc<AllMealsViewMvc.Listener> {
    interface Listener {
        fun addNewMealClicked()
        fun onMealEdit(mealId: Int)
        fun onMealDelete(meal: UiMeal)
    }

    fun bindMeals(newMeals: List<UiMeal>)
}