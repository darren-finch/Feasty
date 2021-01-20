package com.darrenfinch.mymealplanner.common.dialogs.selectfoodformeal.view

import android.app.Dialog
import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood

interface SelectFoodForMealViewMvc : ObservableViewMvc<SelectFoodForMealViewMvc.Listener> {
    interface Listener {
        fun onFoodChosen(food: UiFood)
    }

    fun bindFoods(foods: List<UiFood>)
    fun makeDialog() : Dialog
}