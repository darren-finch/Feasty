package com.darrenfinch.mymealplanner.domain.foodform.view

import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.domain.observables.ObservableFood
import com.darrenfinch.mymealplanner.model.data.entities.Food

interface FoodFormViewMvc : ObservableViewMvc<FoodFormViewMvc.Listener> {
    interface Listener {
        fun onDoneButtonClicked(editedFoodDetails: Food)
    }

    fun bindFoodDetails(observableFood: ObservableFood)
}