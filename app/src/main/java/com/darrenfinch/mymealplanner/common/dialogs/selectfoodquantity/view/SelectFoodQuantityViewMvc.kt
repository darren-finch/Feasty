package com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.view

import android.app.Dialog
import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood
import com.darrenfinch.mymealplanner.foods.models.presentation.UiMacroNutrients
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity

interface SelectFoodQuantityViewMvc : ObservableViewMvc<SelectFoodQuantityViewMvc.Listener> {
    interface Listener {
        fun onPositiveButtonClicked()

        fun onServingSizeQuantityChange(newQuantity: Double)
    }

    fun bindProperties(title: String, macroNutrients: UiMacroNutrients, selectedQuantity: PhysicalQuantity)
    fun bindServingSize(servingSize: PhysicalQuantity)
    fun bindFoodTitle(title: String)
    fun bindMacros(macroNutrients: UiMacroNutrients)
    fun makeDialog() : Dialog
}
