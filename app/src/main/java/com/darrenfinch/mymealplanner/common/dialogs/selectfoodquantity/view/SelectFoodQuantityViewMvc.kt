package com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.view

import android.app.Dialog
import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood

interface SelectFoodQuantityViewMvc : ObservableViewMvc<SelectFoodQuantityViewMvc.Listener> {
    interface Listener {
        fun onFoodServingSizeChosen(selectedFood: UiFood, selectedFoodQuantity: PhysicalQuantity)
        fun onServingSizeChange(newServingSize: PhysicalQuantity)
    }

    fun bindFood(food: UiFood)
    fun makeDialog() : Dialog
    fun getFoodData(): UiFood
}
