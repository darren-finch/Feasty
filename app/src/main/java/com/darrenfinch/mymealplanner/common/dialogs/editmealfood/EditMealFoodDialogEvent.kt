package com.darrenfinch.mymealplanner.common.dialogs.editmealfood

import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood

sealed class EditMealFoodDialogEvent {
    class OnPositiveButtonClicked(val selectedMealFoodResult: UiMealFood, val index: Int) : EditMealFoodDialogEvent() {
        override fun equals(other: Any?): Boolean {
            return other is OnPositiveButtonClicked && selectedMealFoodResult == other.selectedMealFoodResult
        }
        override fun hashCode(): Int {
            return selectedMealFoodResult.hashCode()
        }
    }
}

