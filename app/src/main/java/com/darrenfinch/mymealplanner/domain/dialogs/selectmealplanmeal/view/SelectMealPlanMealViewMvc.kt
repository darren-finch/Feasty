package com.darrenfinch.mymealplanner.domain.dialogs.selectmealplanmeal.view

import android.app.Dialog
import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.model.data.entities.Meal

interface SelectMealPlanMealViewMvc : ObservableViewMvc<SelectMealPlanMealViewMvc.Listener> {
    interface Listener {
        fun onMealSelected(meal: Meal)
    }

    fun bindMeals(meals: List<Meal>)
    fun makeDialog() : Dialog
}