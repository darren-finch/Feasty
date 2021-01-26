package com.darrenfinch.mymealplanner.screens.foodform.view

import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.common.views.ProgressIndicatorViewMvc
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.physicalquantities.units.MeasurementUnit

interface FoodFormViewMvc : ObservableViewMvc<FoodFormViewMvc.Listener>, ProgressIndicatorViewMvc {
    interface Listener {
        fun onDoneButtonClicked()
        fun onNavigateUp()

        fun onTitleChange(newTitle: String)
        fun onServingSizeQuantityChange(newServingSizeQuantity: Double)
        fun onServingSizeUnitChange(newServingSizeUnit: MeasurementUnit)
        fun onCaloriesChange(newCalories: Int)
        fun onCarbsChange(newCarbs: Int)
        fun onFatsChange(newFats: Int)
        fun onProteinsChange(newProteins: Int)
    }

    fun bindFoodDetails(foodDetails: UiFood)
    fun getFoodDetails(): UiFood
}