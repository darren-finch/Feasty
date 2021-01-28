package com.darrenfinch.mymealplanner.screens.allfoods.view

import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.common.views.ProgressIndicatorViewMvc
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood

interface AllFoodsViewMvc : ObservableViewMvc<AllFoodsViewMvc.Listener>, ProgressIndicatorViewMvc {
    interface Listener {
        fun onAddNewFoodClicked()

        fun onFoodEdit(foodId: Int)
        fun onFoodDelete(foodId: Int)
    }

    fun bindFoods(newFoods: List<UiFood>)
}