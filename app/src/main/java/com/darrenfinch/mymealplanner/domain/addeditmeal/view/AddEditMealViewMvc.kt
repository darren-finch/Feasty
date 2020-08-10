package com.darrenfinch.mymealplanner.domain.addeditmeal.view

import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.domain.common.ObservableMeal

interface AddEditMealViewMvc : ObservableViewMvc<AddEditMealViewMvc.Listener> {
    interface Listener {
        fun addNewFoodButtonClicked()
        fun doneButtonClicked()
    }

    fun bindMealDetails(observableMeal: ObservableMeal)
}