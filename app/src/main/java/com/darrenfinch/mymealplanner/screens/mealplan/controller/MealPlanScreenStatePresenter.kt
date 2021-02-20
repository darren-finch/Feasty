package com.darrenfinch.mymealplanner.screens.mealplan.controller

import com.darrenfinch.mymealplanner.common.constants.DefaultModels
import com.darrenfinch.mymealplanner.screens.mealplan.view.MealPlanViewMvc

class MealPlanScreenStatePresenter {
    private lateinit var viewMvc: MealPlanViewMvc

    fun bindView(viewMvc: MealPlanViewMvc) {
        this.viewMvc = viewMvc
    }

    fun presentState(screenState: MealPlanScreenState) {
        when (screenState) {
            is MealPlanScreenState.Loading -> {
                viewMvc.showProgressIndication()
                viewMvc.hideEmptyListIndication()
            }
            is MealPlanScreenState.NoMealPlans -> {
                viewMvc.bindMealPlans(emptyList())
                viewMvc.bindMealsForSelectedMealPlan(emptyList())
                viewMvc.bindMealPlanMacros(DefaultModels.defaultMealPlanMacros)
                viewMvc.hideProgressIndication()
                viewMvc.showEmptyListIndication()
            }
            is MealPlanScreenState.HasMealPlansButNoMealsForSelectedMealPlan -> {
                screenState.let {
                    viewMvc.hideProgressIndication()
                    viewMvc.showEmptyListIndication()
                    viewMvc.bindMealPlans(it.mealPlans)
                    viewMvc.bindMealsForSelectedMealPlan(emptyList())
                    viewMvc.bindMealPlanMacros(it.selectedMealPlanMacros)
                }
            }
            is MealPlanScreenState.HasMealPlansAndMealsForSelectedMealPlan -> {
                screenState.let {
                    viewMvc.hideProgressIndication()
                    viewMvc.bindMealPlans(it.mealPlans)
                    viewMvc.bindMealsForSelectedMealPlan(it.mealsForSelectedMealPlan)
                    viewMvc.bindMealPlanMacros(it.selectedMealPlanMacros)
                }
            }
        }
    }
}