package com.darrenfinch.mymealplanner.common.dialogs.editmealfood.view

import android.app.Dialog
import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.foods.models.domain.MacroNutrients
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity

interface EditMealFoodViewMvc : ObservableViewMvc<EditMealFoodViewMvc.Listener> {
    interface Listener {
        fun onPositiveButtonClicked()
        fun onMealFoodServingSizeQuantityChange(quantity: Double)
    }

    fun bindMealFoodTitle(title: String)
    fun bindMealFoodMacros(macroNutrients: MacroNutrients)
    fun bindMealFoodDesiredServingSize(servingSize: PhysicalQuantity)
    fun makeDialog(): Dialog
}