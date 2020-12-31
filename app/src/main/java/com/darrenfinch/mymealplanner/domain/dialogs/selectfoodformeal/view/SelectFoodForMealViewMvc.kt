package com.darrenfinch.mymealplanner.domain.dialogs.selectfoodformeal.view

import android.app.Dialog
import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.model.data.entities.Food

interface SelectFoodForMealViewMvc : ObservableViewMvc<SelectFoodForMealViewMvc.Listener> {
    interface Listener {
        fun onFoodChosen(food: Food)
    }

    fun bindFoods(foods: List<Food>)
    fun makeDialog() : Dialog
}