package com.darrenfinch.mymealplanner.screens.mealplan.controller

import com.darrenfinch.mymealplanner.common.helpers.SharedPreferencesHelper
import com.darrenfinch.mymealplanner.foods.services.MacroCalculatorService
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlan
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlanMeal
import com.darrenfinch.mymealplanner.mealplans.usecases.GetAllMealPlansUseCase
import com.darrenfinch.mymealplanner.mealplans.usecases.GetMealsForMealPlanUseCase
import com.darrenfinch.mymealplanner.screens.mealplan.MealPlanMacros

class RefreshMealPlanScreenUseCase(
    private val getAllMealPlansUseCase: GetAllMealPlansUseCase,
    private val getMealsForMealPlanUseCase: GetMealsForMealPlanUseCase,
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    private val getValidIndexHelper: GetValidIndexHelper
) {
    sealed class Result {
        data class HasMealPlansAndMealsForSelectedMealPlan(
            val mealPlans: List<UiMealPlan>,
            val selectedMealPlanMacros: MealPlanMacros,
            val selectedMealPlanIndex: Int,
            val mealsForSelectedMealPlan: List<UiMealPlanMeal>
        ) : Result()
        data class HasMealPlansButNoMealsForSelectedMealPlan(
            val mealPlans: List<UiMealPlan>,
            val selectedMealPlanMacros: MealPlanMacros,
            val selectedMealPlanIndex: Int,
        ) : Result()

        object NoMealPlans : Result()
    }

    suspend fun refresh(): Result {
        val allMealPlans = getAllMealPlansUseCase.getAllMealPlans()
        if (allMealPlans.isEmpty()) return Result.NoMealPlans

        val selectedMealPlanIndex = getValidIndexHelper.getValidIndex(
            allMealPlans, sharedPreferencesHelper.getInt(
                MealPlanFragment.SELECTED_MEAL_PLAN_INDEX
            )
        )
        val selectedMealPlan = allMealPlans[selectedMealPlanIndex]

        val mealsForSelectedMealPlan =
            getMealsForMealPlanUseCase.getMealsForMealPlan(selectedMealPlan.id)
        val selectedMealPlanMacros =
            MacroCalculatorService.calculateMealPlanMacros(
                selectedMealPlan,
                mealsForSelectedMealPlan
            )

        return if(mealsForSelectedMealPlan.isEmpty()) {
            Result.HasMealPlansButNoMealsForSelectedMealPlan(
                allMealPlans,
                selectedMealPlanMacros,
                selectedMealPlanIndex
            )
        } else {
            Result.HasMealPlansAndMealsForSelectedMealPlan(
                allMealPlans,
                selectedMealPlanMacros,
                selectedMealPlanIndex,
                mealsForSelectedMealPlan
            )
        }
    }
}