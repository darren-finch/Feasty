package com.darrenfinch.mymealplanner.screens.mealplan

import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.common.helpers.SharedPreferencesHelper
import com.darrenfinch.mymealplanner.foods.services.MacroCalculatorService
import com.darrenfinch.mymealplanner.mealplans.usecases.GetAllMealPlansUseCase
import com.darrenfinch.mymealplanner.mealplans.usecases.GetMealsForMealPlanUseCase
import com.darrenfinch.mymealplanner.screens.mealplan.controller.GetValidIndexHelper
import com.darrenfinch.mymealplanner.screens.mealplan.controller.MealPlanFragment
import com.darrenfinch.mymealplanner.screens.mealplan.controller.RefreshMealPlanScreenUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class RefreshMealPlanScreenUseCaseTest {
    private val selectedMealPlanIndex = 0

    private val getAllMealPlansResult = listOf(
        TestDefModels.defUiMealPlan.copy(title = "defUiMealPlan1"),
        TestDefModels.defUiMealPlan.copy(title = "defUiMealPlan2")
    )
    private val getMealsForMealPlanResult = listOf(
        TestDefModels.defUiMealPlanMeal.copy(title = "defUiMealPlanMeal1"),
        TestDefModels.defUiMealPlanMeal.copy(title = "defUiMealPlanMeal2")
    )

    private val getAllMealPlansUseCase = mockk<GetAllMealPlansUseCase>()
    private val getMealsForMealPlanUseCase = mockk<GetMealsForMealPlanUseCase>()
    private val sharedPreferencesHelper = mockk<SharedPreferencesHelper>()
    private val getValidIndexHelper = mockk<GetValidIndexHelper>()

    private lateinit var SUT: RefreshMealPlanScreenUseCase

    @BeforeEach
    internal fun setUp() {
        SUT = RefreshMealPlanScreenUseCase(
            getAllMealPlansUseCase,
            getMealsForMealPlanUseCase,
            sharedPreferencesHelper,
            getValidIndexHelper
        )
    }


    @Test
    internal fun `refresh() returns no meal plans result if there are no meal plans`() =
        runBlockingTest {
            coEvery { getAllMealPlansUseCase.getAllMealPlans() } returns emptyList()

            assertEquals(RefreshMealPlanScreenUseCase.Result.NoMealPlans, SUT.refresh())
        }

    @Test
    internal fun `refresh() returns correct result if there are meal plans and meals for selected meal plan`() =
        runBlockingTest {
            coEvery { getAllMealPlansUseCase.getAllMealPlans() } returns getAllMealPlansResult
            every { sharedPreferencesHelper.getInt(MealPlanFragment.SELECTED_MEAL_PLAN_INDEX) } returns selectedMealPlanIndex
            every { getValidIndexHelper.getValidIndex(getAllMealPlansResult, any()) } returns selectedMealPlanIndex
            val selectedMealPlan = getAllMealPlansResult[selectedMealPlanIndex]
            coEvery { getMealsForMealPlanUseCase.getMealsForMealPlan(selectedMealPlan.id) } returns getMealsForMealPlanResult
            val selectedMealPlanMacros = MacroCalculatorService.calculateMealPlanMacros(
                selectedMealPlan,
                getMealsForMealPlanResult
            )

            val expectedResult =
                RefreshMealPlanScreenUseCase.Result.HasMealPlansAndMealsForSelectedMealPlan(
                    getAllMealPlansResult,
                    selectedMealPlanMacros,
                    selectedMealPlanIndex,
                    getMealsForMealPlanResult
                )
            assertEquals(expectedResult, SUT.refresh())
        }

    @Test
    internal fun `refresh() returns correct result if there are meal plans but no meals for selected meal plan`() =
        runBlockingTest {
            coEvery { getAllMealPlansUseCase.getAllMealPlans() } returns getAllMealPlansResult
            every { sharedPreferencesHelper.getInt(MealPlanFragment.SELECTED_MEAL_PLAN_INDEX) } returns selectedMealPlanIndex
            every { getValidIndexHelper.getValidIndex(getAllMealPlansResult, any()) } returns selectedMealPlanIndex
            val selectedMealPlan = getAllMealPlansResult[selectedMealPlanIndex]
            coEvery { getMealsForMealPlanUseCase.getMealsForMealPlan(selectedMealPlan.id) } returns emptyList()
            val selectedMealPlanMacros = MacroCalculatorService.calculateMealPlanMacros(
                selectedMealPlan,
                getMealsForMealPlanResult
            )

            val expectedResult =
                RefreshMealPlanScreenUseCase.Result.HasMealPlansButNoMealsForSelectedMealPlan(
                    getAllMealPlansResult,
                    selectedMealPlanMacros,
                    selectedMealPlanIndex,
                )
            assertEquals(expectedResult, SUT.refresh())
        }
}