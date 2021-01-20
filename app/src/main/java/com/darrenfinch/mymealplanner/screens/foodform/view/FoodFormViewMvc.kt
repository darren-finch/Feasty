package com.darrenfinch.mymealplanner.screens.foodform.view

import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood

interface FoodFormViewMvc : ObservableViewMvc<FoodFormViewMvc.Listener> {
    interface Listener {
        fun onDoneButtonClicked(editedFoodDetails: UiFood)
    }

    fun bindFoodDetails(foodDetails: UiFood)
    fun getFoodDetails(): UiFood
}