package com.darrenfinch.mymealplanner.screens.foodform

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.foods.models.presentation.UiFood
import com.darrenfinch.mymealplanner.foods.models.domain.MacroNutrients
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.physicalquantities.units.MeasurementUnit
import java.io.Serializable

class FoodFormData : Serializable {
    private var id = Constants.EXISTING_ITEM_ID
    private var title = ""
    private var servingSizeQuantity = 0.0
    private var servingSizeUnit: MeasurementUnit = MeasurementUnit.defaultUnit
    private var calories = 0
    private var carbs = 0
    private var fats = 0
    private var proteins = 0

    fun bindFoodDetails(food: UiFood) {
        id = food.id
        title = food.title
        servingSizeQuantity = food.servingSize.quantity
        servingSizeUnit = food.servingSize.unit
        calories = food.macros.calories
        carbs = food.macros.carbs
        fats = food.macros.fats
        proteins = food.macros.proteins
    }

    fun getFoodDetails() = UiFood(
        id = id,
        title = title,
        servingSize = PhysicalQuantity(servingSizeQuantity, servingSizeUnit),
        macros = MacroNutrients(
            calories = calories,
            carbs = carbs,
            fats = fats,
            proteins = proteins
        )
    )

    fun setTitle(title: String) {
        this.title = title
    }
    fun setServingSizeQuantity(servingSizeQuantity: Double) {
        this.servingSizeQuantity = servingSizeQuantity
    }
    fun setServingSizeUnit(servingSizeUnit: MeasurementUnit) {
        this.servingSizeUnit = servingSizeUnit
    }
    fun setCalories(calories: Int) {
        this.calories = calories
    }
    fun setCarbs(carbs: Int) {
        this.carbs = carbs
    }
    fun setFats(fats: Int) {
        this.fats = fats
    }
    fun setProteins(proteins: Int) {
        this.proteins = proteins
    }
}