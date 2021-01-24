package com.darrenfinch.mymealplanner.screens.foodform.view

import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.common.views.ProgressIndicatorViewMvc
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood

interface FoodFormViewMvc : ObservableViewMvc<FoodFormViewMvc.Listener>, ProgressIndicatorViewMvc {
    interface Listener {
        fun onDoneButtonClicked(editedFoodDetails: UiFood)
        fun onNavigateUp()
    }

    fun bindFoodDetails(foodDetails: UiFood)
    fun getFoodDetails(): UiFood
}