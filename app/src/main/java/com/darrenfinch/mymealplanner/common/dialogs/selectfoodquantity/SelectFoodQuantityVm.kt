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
    private var originalMacroNutrients = DefaultModels.defaultUiMacroNutrients
    private var servingSizeUnit: MeasurementUnit = MeasurementUnit.defaultUnit
    private var macroNutrients = DefaultModels.defaultUiMacroNutrients

    private var desiredServingSizeQuantity = StatefulVmProperty(0.0, this)

    fun setSelectedFood(food: UiFood) {
        id = food.id
        title = food.title
        originalServingSizeQuantity = food.servingSize.quantity
        servingSizeUnit = food.servingSize.unit
        originalMacroNutrients = food.macroNutrients
    }
    fun getSelectedFood() = UiFood(
        id = id,
        title = title,
        servingSize = PhysicalQuantity(originalServingSizeQuantity, servingSizeUnit),
        macroNutrients = originalMacroNutrients
    )

    fun setDesiredServingSizeQuantity(quantity: Double) {
        desiredServingSizeQuantity.set(quantity)
        macroNutrients = macroNutrientsToUiMacroNutrients(
            MacroCalculator.baseMacrosOnNewServingSize(
                uiMacroNutrientsToMacroNutrients(originalMacroNutrients),
                PhysicalQuantity(originalServingSizeQuantity, servingSizeUnit),
                getDesiredServingSize()
            )
        )
    }
    fun getDesiredServingSize() = PhysicalQuantity(desiredServingSizeQuantity.get(), servingSizeUnit)
    fun getOriginalServingSize() = PhysicalQuantity(originalServingSizeQuantity, servingSizeUnit)
    fun getUpdatedMacros() = macroNutrients
    fun getFoodTitle() = title
}