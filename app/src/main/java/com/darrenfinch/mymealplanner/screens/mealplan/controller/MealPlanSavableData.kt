package com.darrenfinch.mymealplanner.screens.mealplan.controller

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlan
import java.io.Serializable

// TODO: Write test
class MealPlanSavableData : Serializable {
    private var selectedMealPlanIndex = 0
    private var mealPlanIds = mutableListOf<Int>()

    fun setSelectedMealPlanIndex(selectedMealPlanIndex: Int) {
        this.selectedMealPlanIndex = selectedMealPlanIndex
    }

    fun getSelectedMealPlanIndex() = selectedMealPlanIndex

    fun moveSelectedMealPlanIndex() {
        if(0 < selectedMealPlanIndex) selectedMealPlanIndex--
    }

    fun setInitialMealPlans(mealPlans: List<UiMealPlan>) {
        this.mealPlanIds = mealPlans.map { it.id }.toMutableList()
    }

    fun getSelectedMealPlanId(): Int {
        return mealPlanIds.elementAtOrElse(selectedMealPlanIndex) { Constants.INVALID_ID }
    }
}