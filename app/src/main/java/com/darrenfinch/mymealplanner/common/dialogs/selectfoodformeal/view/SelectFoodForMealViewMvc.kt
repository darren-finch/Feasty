package com.darrenfinch.mymealplanner.common.dialogs.selectfoodformeal.view

import android.app.Dialog
import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.foods.models.Food

interface SelectFoodForMealViewMvc : ObservableViewMvc<SelectFoodForMealViewMvc.Listener> {
    interface Listener {
        fun onFoodChosen(food: Food)
    }

    fun bindFoods(foods: List<Food>)
    fun makeDialog() : Dialog
}