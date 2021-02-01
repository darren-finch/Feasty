package com.darrenfinch.mymealplanner.screens.mealplan

import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlan
import java.io.Serializable

class MealPlanVm : Serializable {
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

    fun hasMealPlans() = mealPlanIds.isNotEmpty()

    fun getSelectedMealPlanId(): Int {
        return if (selectedMealPlanIndex < mealPlanIds.size && selectedMealPlanIndex != Constants.INVALID_INDEX)
            mealPlanIds[selectedMealPlanIndex]
        else
            Constants.INVALID_ID
    }
}