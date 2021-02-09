package com.darrenfinch.mymealplanner.common.dialogs.editmealfood

import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood

sealed class EditMealFoodDialogEvent {
    class OnPositiveButtonClicked(val mealFoodResult: UiMealFood) : EditMealFoodDialogEvent() {
        override fun equals(other: Any?): Boolean {
            return other is OnPositiveButtonClicked && mealFoodResult == other.mealFoodResult
        }
        override fun hashCode(): Int {
            return mealFoodResult.hashCode()
        }
    }
}

