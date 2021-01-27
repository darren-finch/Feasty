package com.darrenfinch.mymealplanner.screens.mealplan.view

import com.darrenfinch.mymealplanner.common.views.ListViewMvc
import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.common.views.ProgressIndicatorViewMvc
import com.darrenfinch.mymealplanner.screens.mealplan.MealPlanMacros
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlan
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlanMeal

interface MealPlanViewMvc : ObservableViewMvc<MealPlanViewMvc.Listener>, ProgressIndicatorViewMvc, ListViewMvc {
    interface Listener {
        fun onMealPlanSelected(index: Int)
        fun onAddNewMealPlanClicked()
        fun onDeleteMealPlanClicked()
        fun onAddNewMealPlanMealClicked()
        fun onDeleteMealPlanMealClicked(mealPlanMeal: UiMealPlanMeal)
    }

    fun bindMealPlanMeals(meals: List<UiMealPlanMeal>)
    fun bindMealPlanMacros(mealPlanMacros: MealPlanMacros)
    fun bindMealPlans(mealPlans: List<UiMealPlan>)
    fun hideMealPlans()
    fun showMealPlans()
    fun setSelectedMealPlanIndex(index: Int)
    fun setSelectedMealPlanIndexWithoutNotifying(index: Int)
}
