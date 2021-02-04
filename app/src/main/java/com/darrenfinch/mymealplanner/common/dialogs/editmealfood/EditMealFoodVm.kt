package com.darrenfinch.mymealplanner.common.dialogs.editmealfood

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.ui.viewmodels.StatefulVm
import com.darrenfinch.mymealplanner.common.ui.viewmodels.StatefulVmProperty
import com.darrenfinch.mymealplanner.foods.models.domain.MacroCalculator
import com.darrenfinch.mymealplanner.foods.models.mappers.macroNutrientsToUiMacroNutrients
import com.darrenfinch.mymealplanner.foods.models.mappers.uiMacroNutrientsToMacroNutrients
import com.darrenfinch.mymealplanner.foods.models.presentation.UiMacroNutrients
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.physicalquantities.units.MeasurementUnit

class EditMealFoodVm : StatefulVm() {
    private var id = Constants.INVALID_ID
    private var foodId = Constants.INVALID_ID
    private var mealId = Constants.INVALID_ID
    private var title = ""
    private var desiredServingSizeQuantity = StatefulVmProperty(0.0, this)
    private var desiredServingSizeUnit =
        StatefulVmProperty<MeasurementUnit>(MeasurementUnit.defaultUnit, this)
    private var originalServingSizeQuantity = 0.0
    private var originalServingSizeUnit: MeasurementUnit = MeasurementUnit.defaultUnit
    private var calories = 0
    private var carbs = 0
    private var fats = 0
    private var proteins = 0

    private val desiredServingSize: PhysicalQuantity
        get() = PhysicalQuantity(desiredServingSizeQuantity.get(), desiredServingSizeUnit.get())
    private val updatedMacroNutrients: UiMacroNutrients
        get() = macroNutrientsToUiMacroNutrients(
            MacroCalculator.baseMacrosOnNewServingSize(
                uiMacroNutrientsToMacroNutrients(originalMacroNutrients),
                originalServingSize,
                desiredServingSize
            )
        )

    private val originalServingSize: PhysicalQuantity
        get() = PhysicalQuantity(originalServingSizeQuantity, originalServingSizeUnit)
    private val originalMacroNutrients: UiMacroNutrients
        get() = UiMacroNutrients(
            calories = calories,
            carbs = carbs,
            fats = fats,
            proteins = proteins
        )

    fun bindMealFoodDetails(mealFood: UiMealFood) {
        id = mealFood.id
        foodId = mealFood.foodId
        mealId = mealFood.mealId
        title = mealFood.title
        desiredServingSizeQuantity.set(mealFood.desiredServingSize.quantity)
        desiredServingSizeUnit.set(mealFood.desiredServingSize.unit)
        originalServingSizeQuantity = mealFood.originalServingSize.quantity
        originalServingSizeUnit = mealFood.originalServingSize.unit
        calories = mealFood.originalMacroNutrients.calories
        carbs = mealFood.originalMacroNutrients.carbs
        fats = mealFood.originalMacroNutrients.fats
        proteins = mealFood.originalMacroNutrients.proteins
    }

    fun getMealFoodDetails() = UiMealFood(
        id = id,
        foodId = foodId,
        mealId = mealId,
        title = title,
        originalServingSize = originalServingSize,
        desiredServingSize = desiredServingSize,
        originalMacroNutrients = originalMacroNutrients
    )

    fun getMealFoodTitle() = title

    fun getUpdatedMealFoodMacros() = updatedMacroNutrients

    fun getDesiredMealFoodServingSize() = desiredServingSize

    fun setDesiredServingSizeQuantity(servingSizeQuantity: Double) =
        this.desiredServingSizeQuantity.set(servingSizeQuantity)
}