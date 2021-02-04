package com.darrenfinch.mymealplanner.common.dialogs.editmealfood.view

import android.app.Dialog
import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.common.views.ProgressIndicatorViewMvc
import com.darrenfinch.mymealplanner.foods.models.presentation.UiMacroNutrients
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity

interface EditMealFoodViewMvc : ObservableViewMvc<EditMealFoodViewMvc.Listener> {
    interface Listener {
        fun onPositiveButtonClicked()
        fun onMealFoodServingSizeQuantityChange(quantity: Double)
    }

    fun bindMealFoodTitle(title: String)
    fun bindMealFoodMacros(macroNutrients: UiMacroNutrients)
    fun bindMealFoodDesiredServingSize(servingSize: PhysicalQuantity)
    fun makeDialog(): Dialog
}