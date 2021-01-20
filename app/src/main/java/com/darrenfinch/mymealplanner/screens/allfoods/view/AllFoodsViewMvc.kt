package com.darrenfinch.mymealplanner.screens.allfoods.view

import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood

interface AllFoodsViewMvc : ObservableViewMvc<AllFoodsViewMvc.Listener> {
    interface Listener {
        fun addNewFoodClicked()

        fun onItemEdit(foodId: Int)
        fun onItemDelete(foodId: Int)
    }

    fun bindFoods(newFoods: List<UiFood>)
}