package com.darrenfinch.mymealplanner.domain.allfoods.view

import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.model.data.entities.Food

interface AllFoodsViewMvc : ObservableViewMvc<AllFoodsViewMvc.Listener> {
    interface Listener {
        fun addNewFoodClicked()

        fun onItemEdit(foodId: Int)
        fun onItemDelete(foodId: Int)
    }

    fun bindFoods(newFoods: List<Food>)
}