package com.darrenfinch.mymealplanner.screens.mealplan.view

import com.darrenfinch.mymealplanner.common.views.ListViewMvc
import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.common.views.ProgressIndicatorViewMvc
import com.darrenfinch.mymealplanner.screens.mealplan.MealPlanMacros
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlan
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlanMeal
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal

interface MealPlanViewMvc : ObservableViewMvc<MealPlanViewMvc.Listener>, ProgressIndicatorViewMvc, ListViewMvc {
    interface Listener {
        fun onMealPlanSelectedByUser(index: Int)
        fun onAddNewMealPlanClicked()
        fun onDeleteMealPlanClicked()
        fun onAddNewMealPlanMealClicked()
        fun onDeleteMealPlanMealClicked(mealPlanMealId: Int)
    }

    fun bindMealsForSelectedMealPlan(meals: List<UiMealPlanMeal>)
    fun bindMealPlanMacros(mealPlanMacros: MealPlanMacros)
    fun bindMealPlans(mealPlans: List<UiMealPlan>)

    // This does not notify the caller that they selected this index
    fun setSelectedMealPlanIndex(index: Int)
}
