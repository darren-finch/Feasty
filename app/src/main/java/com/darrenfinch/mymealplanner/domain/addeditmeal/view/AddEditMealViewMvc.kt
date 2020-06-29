package com.darrenfinch.mymealplanner.domain.addeditmeal.view

import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc

interface AddEditMealViewMvc : ObservableViewMvc<AddEditMealViewMvc.Listener> {
    interface Listener {
        fun addNewFoodClicked()
    }
}