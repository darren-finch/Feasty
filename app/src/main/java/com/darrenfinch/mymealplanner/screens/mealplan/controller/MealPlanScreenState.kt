package com.darrenfinch.mymealplanner.screens.mealplan.controller

import androidx.annotation.StringRes
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlan
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlanMeal
import com.darrenfinch.mymealplanner.screens.mealplan.MealPlanMacros
import java.io.Serializable

sealed class MealPlanScreenState : Serializable {
    object Loading : MealPlanScreenState()
    class HasMealPlansAndMealsForSelectedMealPlan(
        val mealPlans: List<UiMealPlan>,
        val mealsForSelectedMealPlan: List<UiMealPlanMeal>,
        val selectedMealPlanMacros: MealPlanMacros,
        val selectedMealPlanIndex: Int
    ) : MealPlanScreenState()

    class HasMealPlansButNoMealsForSelectedMealPlan(
        val mealPlans: List<UiMealPlan>,
        val selectedMealPlanMacros: MealPlanMacros,
        val selectedMealPlanIndex: Int
    ) : MealPlanScreenState()

    object NoMealPlans : MealPlanScreenState()
}