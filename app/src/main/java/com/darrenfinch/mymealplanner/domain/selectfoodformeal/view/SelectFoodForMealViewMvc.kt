package com.darrenfinch.mymealplanner.domain.selectfoodformeal.view

import android.app.Dialog
import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.model.data.Food

interface SelectFoodForMealViewMvc : ObservableViewMvc<SelectFoodForMealViewMvc.Listener> {
    interface Listener {
        fun onFoodChosen(foodId: Int)
    }

    fun bindFoods(foodsFromDatabase: List<Food>)
    fun makeDialog() : Dialog
}