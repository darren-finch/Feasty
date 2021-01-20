package com.darrenfinch.mymealplanner.common.dialogs.selectmealplanmeal.view

import android.app.Dialog
import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.meals.models.Meal

interface SelectMealPlanMealViewMvc : ObservableViewMvc<SelectMealPlanMealViewMvc.Listener> {
    interface Listener {
        fun onMealSelected(selectedMeal: Meal)
    }

    fun bindMeals(meals: List<Meal>)
    fun makeDialog() : Dialog
}