package com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.view

import android.app.Dialog
import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.foods.models.Food

interface SelectFoodQuantityViewMvc : ObservableViewMvc<SelectFoodQuantityViewMvc.Listener> {
    interface Listener {
        fun onFoodServingSizeChosen(selectedFood: Food, selectedFoodQuantity: PhysicalQuantity)
    }

    fun bindFood(food: Food)
    fun makeDialog() : Dialog

    // TODO: Remove when view models are introduced
    fun getFoodData(): Food
}
