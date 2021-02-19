package com.darrenfinch.mymealplanner.common.dialogs.editmealfood

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.foods.services.MacroCalculatorService
import com.darrenfinch.mymealplanner.foods.models.mappers.macroNutrientsToUiMacroNutrients
import com.darrenfinch.mymealplanner.foods.models.mappers.uiMacroNutrientsToMacroNutrients
import com.darrenfinch.mymealplanner.foods.models.presentation.UiMacroNutrients
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.physicalquantities.units.MeasurementUnit
import java.io.Serializable

class EditMealFoodDialogData : Serializable {
    private var id = Constants.INVALID_ID
    private var foodId = Constants.INVALID_ID
    private var mealId = Constants.INVALID_ID

    private var title = ""

    private var desiredServingSizeQuantity = 0.0
    private var desiredServingSizeUnit: MeasurementUnit = MeasurementUnit.defaultUnit

    private var originalServingSizeQuantity = 0.0
    private var originalServingSizeUnit: MeasurementUnit = MeasurementUnit.defaultUnit

    private var calories = 0
    private var carbs = 0
    private var fats = 0
    private var proteins = 0

    private fun getOriginalServingSize() =
        PhysicalQuantity(originalServingSizeQuantity, originalServingSizeUnit)

    private fun getOriginalMacroNutrients() = UiMacroNutrients(
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
        desiredServingSizeQuantity = mealFood.desiredServingSize.quantity
        desiredServingSizeUnit = mealFood.desiredServingSize.unit
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
        originalServingSize = getOriginalServingSize(),
        desiredServingSize = getDesiredServingSize(),
        originalMacroNutrients = getOriginalMacroNutrients()
    )

    fun getTitle() = title

    fun getMacrosBasedOnDesiredServingSize() = macroNutrientsToUiMacroNutrients(
        MacroCalculatorService.baseMacrosOnNewServingSize(
            uiMacroNutrientsToMacroNutrients(getOriginalMacroNutrients()),
            getOriginalServingSize(),
            getDesiredServingSize()
        )
    )

    fun getDesiredServingSize() =
        PhysicalQuantity(desiredServingSizeQuantity, desiredServingSizeUnit)

    fun setDesiredServingSizeQuantity(servingSizeQuantity: Double) {
        this.desiredServingSizeQuantity = servingSizeQuantity
    }
}