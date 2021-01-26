package com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.constants.DefaultModels
import com.darrenfinch.mymealplanner.common.ui.viewmodels.StatefulVm
import com.darrenfinch.mymealplanner.common.ui.viewmodels.StatefulVmProperty
import com.darrenfinch.mymealplanner.foods.models.domain.MacroCalculator
import com.darrenfinch.mymealplanner.foods.models.mappers.macroNutrientsToUiMacroNutrients
import com.darrenfinch.mymealplanner.foods.models.mappers.uiMacroNutrientsToMacroNutrients
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.physicalquantities.units.MeasurementUnit

class SelectFoodQuantityVm : StatefulVm() {

    private var id = Constants.VALID_ID
    private var title = ""
    private var originalServingSizeQuantity = 0.0
    private var desiredServingSizeQuantity = StatefulVmProperty(0.0, this)
    private var servingSizeUnit: MeasurementUnit = MeasurementUnit.defaultUnit
    private var macroNutrients = DefaultModels.defaultUiMacroNutrients

    fun setSelectedFood(food: UiFood) {
        id = 0
        title = food.title
        originalServingSizeQuantity = food.servingSize.quantity
        servingSizeUnit = food.servingSize.unit
        macroNutrients = food.macroNutrients
    }

    fun getSelectedFood() = UiFood(
        id = id,
        title = title,
        servingSize = PhysicalQuantity(originalServingSizeQuantity, servingSizeUnit),
        macroNutrients = macroNutrients
    )

    fun setSelectedFoodQuantity(quantity: Double) {
        desiredServingSizeQuantity.set(quantity)
        macroNutrients = macroNutrientsToUiMacroNutrients(
            MacroCalculator.baseMacrosOnNewServingSize(
                uiMacroNutrientsToMacroNutrients(macroNutrients),
                PhysicalQuantity(originalServingSizeQuantity, servingSizeUnit),
                getSelectedServingSize()
            )
        )
    }

    fun getSelectedServingSize() = PhysicalQuantity(desiredServingSizeQuantity.get(), servingSizeUnit)
}