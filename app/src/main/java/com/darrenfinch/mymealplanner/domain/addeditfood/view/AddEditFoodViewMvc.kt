package com.darrenfinch.mymealplanner.domain.addeditfood.view

import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.domain.common.ObservableFood
import com.darrenfinch.mymealplanner.model.data.entities.Food

interface AddEditFoodViewMvc : ObservableViewMvc<AddEditFoodViewMvc.Listener> {
    interface Listener {
        fun onDoneButtonClicked(editedFoodDetails: Food)
    }

    fun bindFoodDetails(observableFood: ObservableFood)
}