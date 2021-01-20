package com.darrenfinch.mymealplanner.screens.mealplan.view

import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.mealplans.models.MealPlan
import com.darrenfinch.mymealplanner.screens.mealplan.MealPlanMacros
import com.darrenfinch.mymealplanner.mealplans.models.MealPlanMeal

interface MealPlanViewMvc : ObservableViewMvc<MealPlanViewMvc.Listener> {
    interface Listener {
        fun onMealPlanSelected(index: Int)
        fun onAddNewMealPlanClicked()
        fun onDeleteMealPlanClicked()
        fun onAddNewMealPlanMealClicked()
        fun onDeleteMealPlanMealClicked(mealPlanMeal: MealPlanMeal)
    }

    fun bindMealPlanMeals(meals: List<MealPlanMeal>)
    fun bindMealPlanMacros(mealPlanMacros: MealPlanMacros)
    fun bindMealPlans(mealPlans: List<MealPlan>)
    fun setSelectedMealPlanIndex(index: Int)
    fun hideMealPlans()
    fun showMealPlans()
}