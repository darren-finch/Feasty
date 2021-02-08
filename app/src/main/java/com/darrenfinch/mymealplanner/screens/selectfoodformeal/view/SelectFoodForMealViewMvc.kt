package com.darrenfinch.mymealplanner.screens.selectfoodformeal.view

import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood

interface SelectFoodForMealViewMvc : ObservableViewMvc<SelectFoodForMealViewMvc.Listener> {
    interface Listener {
        fun onNavigateUp()
        fun onFoodChosen(food: UiFood)
    }

    fun bindFoods(foods: List<UiFood>)
}