package com.darrenfinch.mymealplanner.screens.mealplanform.view

import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.common.views.ProgressIndicatorViewMvc
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlan

interface MealPlanFormViewMvc : ObservableViewMvc<MealPlanFormViewMvc.Listener>, ProgressIndicatorViewMvc {
    interface Listener {
        fun onDoneButtonClicked()
        fun onNavigateUp()

        fun onTitleChange(newTitle: String)
        fun onRequiredCaloriesChange(newRequiredCalories: Int)
        fun onRequiredCarbohydratesChange(newRequiredCarbohydrates: Int)
        fun onRequiredFatsChange(newRequiredFats: Int)
        fun onRequiredProteinsChange(newRequiredProteins: Int)
    }

    fun bindMealPlanDetails(mealPlan: UiMealPlan)
}