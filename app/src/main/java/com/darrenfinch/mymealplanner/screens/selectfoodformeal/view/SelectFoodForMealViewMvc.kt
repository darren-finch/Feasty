package com.darrenfinch.mymealplanner.screens.selectfoodformeal.view

import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.common.views.ProgressIndicatorViewMvc
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood

interface SelectFoodForMealViewMvc : ObservableViewMvc<SelectFoodForMealViewMvc.Listener>, ProgressIndicatorViewMvc {
    interface Listener {
        fun onNavigateUp()
        fun onFoodChosen(food: UiFood)
        fun onQuerySubmitted(query: String)
    }

    fun bindFoods(foods: List<UiFood>)
}