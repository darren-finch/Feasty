package com.darrenfinch.mymealplanner.domain.selectfoodformeal.view

import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.model.data.Food

interface SelectFoodForMealViewMvc : ObservableViewMvc<SelectFoodForMealViewMvc.Listener> {
    interface Listener {

    }

    fun bindFoods(foodsFromDatabase: List<Food>)
}