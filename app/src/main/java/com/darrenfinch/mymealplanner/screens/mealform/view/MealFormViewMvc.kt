package com.darrenfinch.mymealplanner.screens.mealform.view

import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.common.views.ProgressIndicatorViewMvc
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMealFood

interface MealFormViewMvc : ObservableViewMvc<MealFormViewMvc.Listener>, ProgressIndicatorViewMvc {
    interface Listener {
        fun onMealFoodEdit(mealFood: UiMealFood)
        fun onMealFoodDelete(id: Int)

        fun onAddNewFoodButtonClicked()
        fun onDoneButtonClicked()
        fun onNavigateUp()

        fun onTitleChange(newTitle: String)
    }

    fun bindMealDetails(mealDetails: UiMeal)
}