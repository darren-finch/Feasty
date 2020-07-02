package com.darrenfinch.mymealplanner.domain.selectfoodformeal.view

import android.app.AlertDialog
import android.app.Dialog
import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.model.data.Food

interface SelectFoodForMealViewMvc : ObservableViewMvc<SelectFoodForMealViewMvc.Listener> {
    interface Listener {

    }

    //TODO: Remove this when you get proper dialog implementation into your codebase.
    fun makeDialog() : Dialog
    fun bindFoods(foodsFromDatabase: List<Food>)
}