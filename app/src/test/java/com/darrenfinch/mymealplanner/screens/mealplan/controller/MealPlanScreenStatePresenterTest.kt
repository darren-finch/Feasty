package com.darrenfinch.mymealplanner.screens.mealplan.controller

import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.screens.mealplan.view.MealPlanViewMvc
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class MealPlanScreenStatePresenterTest {
    private val viewMvc = mockk<MealPlanViewMvc>(relaxUnitFun = true)

    private val SUT = MealPlanScreenStatePresenter()

    @BeforeEach
    internal fun setUp() {
        SUT.bindView(viewMvc)
    }

    // loading - show progress indication and hide empty list indication
    @Test
    internal fun `presentState() - loading - show progress indication and hides empty list indication`() {
        val state = MealPlanScreenState.Loading

        SUT.presentState(state)

        verify {
            viewMvc.showProgressIndication()
            viewMvc.hideEmptyListIndication()
        }
    }

    @Test
    internal fun `presentState() - no meal plans - hide progress indication, show empty list indication, and bind default data`() {
        val state = MealPlanScreenState.NoMealPlans

        SUT.presentState(state)

        verify {
            viewMvc.hideProgressIndication()
            viewMvc.showEmptyListIndication()
            viewMvc.bindMealPlans(emptyList())
            viewMvc.bindMealsForSelectedMealPlan(emptyList())
            viewMvc.bindMealPlanMacros(TestDefModels.defMealPlanMacros)
        }
    }

    @Test
    internal fun `presentState() - has meal plans but no meals for selected meal plan - hide progress indication, show meal plans, show meal plan macros, and show empty list indication`() {
        val mealPlans = listOf(TestDefModels.defUiMealPlan)
        val selectedMealPlanMacros = TestDefModels.defMealPlanMacros
        val state = MealPlanScreenState.HasMealPlansButNoMealsForSelectedMealPlan(
            mealPlans,
            selectedMealPlanMacros,
            -1 // doesn't matter for this test
        )

        SUT.presentState(state)

        verify {
            viewMvc.hideProgressIndication()
            viewMvc.showEmptyListIndication()
            viewMvc.bindMealPlans(mealPlans)
            viewMvc.bindMealPlanMacros(selectedMealPlanMacros)
        }
    }

    @Test
    internal fun `presentState() - has meal plans and meals for selected meal plan - hide progress indication, show meal plans, show meal plan macros, and show meals for selected meal plan`() {
        val mealPlans = listOf(TestDefModels.defUiMealPlan)
        val mealsForSelectedMealPlan = listOf(TestDefModels.defUiMealPlanMeal)
        val selectedMealPlanMacros = TestDefModels.defMealPlanMacros
        val state = MealPlanScreenState.HasMealPlansAndMealsForSelectedMealPlan(
            mealPlans,
            mealsForSelectedMealPlan,
            selectedMealPlanMacros,
            -1 // doesn't matter for this test
        )

        SUT.presentState(state)

        verify {
            viewMvc.hideProgressIndication()
            viewMvc.bindMealPlans(mealPlans)
            viewMvc.bindMealPlanMacros(selectedMealPlanMacros)
            viewMvc.bindMealsForSelectedMealPlan(mealsForSelectedMealPlan)
        }
    }
}