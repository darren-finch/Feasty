package com.darrenfinch.mymealplanner.screens.mealplan.controller

import com.darrenfinch.mymealplanner.TestConstants
import com.darrenfinch.mymealplanner.TestDefaultModels
import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.common.helpers.SharedPreferencesHelper
import com.darrenfinch.mymealplanner.common.helpers.ToastsHelper
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.foods.models.domain.MacroCalculator
import com.darrenfinch.mymealplanner.foods.models.presentation.UiMacroNutrients
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlan
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlanMeal
import com.darrenfinch.mymealplanner.mealplans.usecases.*
import com.darrenfinch.mymealplanner.screens.mealplan.MealPlanMacros
import com.darrenfinch.mymealplanner.screens.mealplan.MealPlanVm
import com.darrenfinch.mymealplanner.screens.mealplan.view.MealPlanViewMvc
import com.darrenfinch.mymealplanner.testrules.CoroutinesTestExtension
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@Suppress("RemoveRedundantQualifierName")
@ExperimentalCoroutinesApi
internal class MealPlanControllerImplTest {
    val defUiMealPlan = TestDefaultModels.defUiMealPlan.copy(title = "defUiMealPlan")
    val defUiMealPlan2 = TestDefaultModels.defUiMealPlan.copy(title = "defUiMealPlan2")

    val defUiMealPlanMeal = TestDefaultModels.defUiMealPlanMeal

    val getAllMealPlansResult = listOf(defUiMealPlan)
    val getMealPlanUseCaseResult =
        TestDefaultModels.defUiMealPlan.copy(title = "getMealPlanUseCaseResult")
    val getMealsForMealPlanResult = listOf(defUiMealPlanMeal)

    val defSelectedMealPlanIndex = TestConstants.DEFAULT_INDEX
    val defSelectedMealPlanId = TestConstants.VALID_ID

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    private val viewModel = mockk<MealPlanVm>(relaxUnitFun = true)
    private val getMealPlanUseCase = mockk<GetMealPlanUseCase>(relaxUnitFun = true)
    private val getAllMealPlansUseCase = mockk<GetAllMealPlansUseCase>(relaxUnitFun = true)
    private val getMealsForMealPlanUseCase = mockk<GetMealsForMealPlanUseCase>(relaxUnitFun = true)
    private val deleteMealPlanUseCase = mockk<DeleteMealPlanUseCase>(relaxUnitFun = true)
    private val insertMealPlanMealUseCase = mockk<InsertMealPlanMealUseCase>(relaxUnitFun = true)
    private val deleteMealPlanMealUseCase = mockk<DeleteMealPlanMealUseCase>(relaxUnitFun = true)
    private val screensNavigator = mockk<ScreensNavigator>(relaxUnitFun = true)
    private val toastsHelper = mockk<ToastsHelper>(relaxUnitFun = true)
    private val dialogsManager = mockk<DialogsManager>(relaxUnitFun = true)
    private val dialogsEventBus = mockk<DialogsEventBus>(relaxUnitFun = true)
    private val sharedPreferencesHelper = mockk<SharedPreferencesHelper>(relaxUnitFun = true)

    val viewMvc = mockk<MealPlanViewMvc>(relaxUnitFun = true)

    private lateinit var SUT: MealPlanControllerImpl

    @BeforeEach
    internal fun setUp() {
        SUT = MealPlanControllerImpl(
            viewModel,
            getMealPlanUseCase,
            getAllMealPlansUseCase,
            getMealsForMealPlanUseCase,
            insertMealPlanMealUseCase,
            deleteMealPlanUseCase,
            deleteMealPlanMealUseCase,
            screensNavigator,
            toastsHelper,
            dialogsManager,
            dialogsEventBus,
            sharedPreferencesHelper,
            coroutinesTestExtension.testDispatcher,
            coroutinesTestExtension.testDispatcher
        )

        SUT.bindView(viewMvc)

        every { sharedPreferencesHelper.getInt(MealPlanFragment.SELECTED_MEAL_PLAN_INDEX) } returns defSelectedMealPlanIndex
        every { viewModel.getSelectedMealPlanIndex() } returns defSelectedMealPlanIndex
        every { viewModel.getSelectedMealPlanId() } returns defSelectedMealPlanId
        coEvery { getAllMealPlansUseCase.getAllMealPlans() } returns getAllMealPlansResult
        coEvery { getMealPlanUseCase.getMealPlan(any()) } returns getMealPlanUseCaseResult
        coEvery { getMealsForMealPlanUseCase.getMealsForMealPlan(any()) } returns getMealsForMealPlanResult
    }

    @Test
    internal fun `getAllMealPlans() shows then hides progress indication()`() = runBlockingTest {
        SUT.getAllMealPlans()

        verifyOrder {
            viewMvc.showProgressIndication()
            viewMvc.hideProgressIndication()
        }
    }

    @Test
    internal fun `getAllMealPlans() hides empty list indication if there are meal plans to display`() =
        runBlockingTest {
            SUT.getAllMealPlans()

            verify { viewMvc.hideEmptyListIndication() }
        }

    @Test
    internal fun `getAllMealPlans() binds meal plans from use case to view if there are meal plans to display`() =
        runBlockingTest {
            SUT.getAllMealPlans()

            verify { viewMvc.bindMealPlans(getAllMealPlansResult) }
        }

    @Test
    internal fun `getAllMealPlans() refreshes meal plan meals for selected meal plan if there are meals to display`() =
        runBlockingTest {
            SUT.getAllMealPlans()

            verify { viewMvc.bindMealPlanMeals(getMealsForMealPlanResult) }
        }

    @Test
    internal fun `getAllMealPlans() binds meal plan macros if there are meals to display`() = runBlockingTest {
        val mealPlan = UiMealPlan(
            id = 0,
            title = "",
            requiredCalories = 2000,
            requiredProteins = 175,
            requiredFats = 60,
            requiredCarbs = 250
        )
        val mealPlanMeals = listOf(
            TestDefaultModels.defUiMealPlanMeal.copy(
                foods = listOf(
                    TestDefaultModels.defUiMealFood.copy(
                        macroNutrients = UiMacroNutrients(
                            800,
                            80,
                            80,
                            30
                        )
                    ),
                    TestDefaultModels.defUiMealFood.copy(
                        macroNutrients = UiMacroNutrients(
                            800,
                            80,
                            80,
                            30
                        )
                    )
                )
            )
        )
        val mealPlanMacros = MacroCalculator.calculateMealPlanMacros(mealPlan, mealPlanMeals)
        coEvery { getMealPlanUseCase.getMealPlan(any()) } returns mealPlan
        coEvery { getMealsForMealPlanUseCase.getMealsForMealPlan(any()) } returns mealPlanMeals

        SUT.getAllMealPlans()

        verify { viewMvc.bindMealPlanMacros(mealPlanMacros) }
    }

//    @Test
//    internal fun `getAllMealPlans() doesn't get meals for selected meal plan if there are no meals to display`() = runBlockingTest {
//        coEvery { getAllMealPlansUseCase.getAllMealPlans() } returns listOf()
//
//        SUT.getAllMealPlans()
//
//        verify { viewModel. }
//    }
}