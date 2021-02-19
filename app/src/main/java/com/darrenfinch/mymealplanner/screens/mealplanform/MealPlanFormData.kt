package com.darrenfinch.mymealplanner.screens.mealplanform

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlan
import java.io.Serializable

class MealPlanFormData : Serializable {
    private var id = Constants.NEW_ITEM_ID
    private var title = ""
    private var requiredCalories = 0
    private var requiredCarbs = 0
    private var requiredFats = 0
    private var requiredProteins = 0

    fun setMealPlanDetails(mealPlan: UiMealPlan) {
        id = mealPlan.id
        title = mealPlan.title
        requiredCalories = mealPlan.requiredCalories
        requiredCarbs = mealPlan.requiredCarbs
        requiredFats = mealPlan.requiredFats
        requiredProteins = mealPlan.requiredProteins
    }

    fun getMealPlanDetails() = UiMealPlan(
        id = id,
        title = title,
        requiredCalories = requiredCalories,
        requiredCarbs = requiredCarbs,
        requiredFats = requiredFats,
        requiredProteins = requiredProteins
    )

    fun setTitle(title: String) {
        this.title = title
    }

    fun setRequiredCalories(requiredCalories: Int) {
        this.requiredCalories = requiredCalories
    }

    fun setRequiredCarbohydrates(requiredCarbs: Int) {
        this.requiredCarbs = requiredCarbs
    }

    fun setRequiredFats(requiredFats: Int) {
        this.requiredFats = requiredFats
    }

    fun setRequiredProteins(requiredProteins: Int) {
        this.requiredProteins = requiredProteins
    }
}