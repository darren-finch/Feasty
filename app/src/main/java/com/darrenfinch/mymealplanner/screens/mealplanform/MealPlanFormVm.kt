package com.darrenfinch.mymealplanner.screens.mealplanform

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.ui.viewmodels.StatefulVm
import com.darrenfinch.mymealplanner.common.ui.viewmodels.StatefulVmProperty
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlan

class MealPlanFormVm : StatefulVm() {
    private var id = Constants.EXISTING_ITEM_ID
    private var title = StatefulVmProperty("", this)
    private var requiredCalories = StatefulVmProperty(0, this)
    private var requiredCarbohydrates = StatefulVmProperty(0, this)
    private var requiredFats = StatefulVmProperty(0, this)
    private var requiredProteins = StatefulVmProperty(0, this)

    fun setDefaultState(mealPlan: UiMealPlan) {
        id = mealPlan.id
        title.setWithoutNotifying(mealPlan.title)
        requiredCalories.setWithoutNotifying(mealPlan.requiredCalories)
        requiredCarbohydrates.setWithoutNotifying(mealPlan.requiredCarbs)
        requiredFats.setWithoutNotifying(mealPlan.requiredFats)
        requiredProteins.setWithoutNotifying(mealPlan.requiredProteins)
    }

    fun getState() = UiMealPlan(
        id = id,
        title = title.get(),
        requiredCalories = requiredCalories.get(),
        requiredCarbs = requiredCarbohydrates.get(),
        requiredFats = requiredFats.get(),
        requiredProteins = requiredProteins.get()
    )

    fun setTitle(newTitle: String) {
        title.set(newTitle)
    }

    fun setRequiredCalories(newRequiredCalories: Int) {
        requiredCalories.set(newRequiredCalories)
    }

    fun setRequiredCarbohydrates(newRequiredCarbohydrates: Int) {
        requiredCarbohydrates.set(newRequiredCarbohydrates)
    }

    fun setRequiredFats(newRequiredFats: Int) {
        requiredFats.set(newRequiredFats)
    }

    fun setRequiredProteins(newRequiredProteins: Int) {
        requiredProteins.set(newRequiredProteins)
    }
}