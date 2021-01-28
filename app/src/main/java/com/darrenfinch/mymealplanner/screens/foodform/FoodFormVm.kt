package com.darrenfinch.mymealplanner.screens.foodform

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.ui.viewmodels.StatefulVm
import com.darrenfinch.mymealplanner.common.ui.viewmodels.StatefulVmProperty
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood
import com.darrenfinch.mymealplanner.foods.models.presentation.UiMacroNutrients
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.physicalquantities.units.MeasurementUnit

class FoodFormVm : StatefulVm() {
    private var id = Constants.VALID_ID
    private var title = StatefulVmProperty("", this)
    private var servingSizeQuantity = StatefulVmProperty(0.0, this)
    private var servingSizeUnit = StatefulVmProperty<MeasurementUnit>(MeasurementUnit.defaultUnit, this)
    private var calories = StatefulVmProperty(0, this)
    private var carbs = StatefulVmProperty(0, this)
    private var fats = StatefulVmProperty(0, this)
    private var proteins = StatefulVmProperty(0, this)

    fun bindInitialFoodDetails(food: UiFood) {
        id = food.id
        title.set(food.title)
        servingSizeQuantity.set(food.servingSize.quantity)
        servingSizeUnit.set(food.servingSize.unit)
        calories.set(food.macroNutrients.calories)
        carbs.set(food.macroNutrients.carbs)
        fats.set(food.macroNutrients.fats)
        proteins.set(food.macroNutrients.proteins)
    }

    fun getFoodDetails() = UiFood(
        id = id,
        title = title.get(),
        servingSize = PhysicalQuantity(servingSizeQuantity.get(), servingSizeUnit.get()),
        macroNutrients = UiMacroNutrients(
            calories = calories.get(),
            carbs = carbs.get(),
            fats = fats.get(),
            proteins = proteins.get()
        )
    )

    fun setTitle(title: String) = this.title.set(title)
    fun setServingSizeQuantity(servingSizeQuantity: Double) = this.servingSizeQuantity.set(servingSizeQuantity)
    fun setServingSizeUnit(servingSizeUnit: MeasurementUnit) = this.servingSizeUnit.set(servingSizeUnit)
    fun setCalories(calories: Int) = this.calories.set(calories)
    fun setCarbs(carbs: Int) = this.carbs.set(carbs)
    fun setFats(fats: Int) = this.fats.set(fats)
    fun setProteins(proteins: Int) = this.proteins.set(proteins)
}