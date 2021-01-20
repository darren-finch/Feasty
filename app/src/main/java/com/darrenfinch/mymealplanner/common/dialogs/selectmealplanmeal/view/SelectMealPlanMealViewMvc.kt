package com.darrenfinch.mymealplanner.common.dialogs.selectmealplanmeal.view

import android.app.Dialog
import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal

interface SelectMealPlanMealViewMvc : ObservableViewMvc<SelectMealPlanMealViewMvc.Listener> {
    interface Listener {
        fun onMealSelected(selectedMeal: UiMeal)
    }

    fun bindMeals(meals: List<UiMeal>)
    fun makeDialog() : Dialog
}