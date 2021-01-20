package com.darrenfinch.mymealplanner.screens.foodform.view

import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.foods.models.Food

interface FoodFormViewMvc : ObservableViewMvc<FoodFormViewMvc.Listener> {
    interface Listener {
        fun onDoneButtonClicked(editedFoodDetails: Food)
    }

    fun bindFoodDetails(foodDetails: Food?)
    fun getFoodDetails(): Food
}