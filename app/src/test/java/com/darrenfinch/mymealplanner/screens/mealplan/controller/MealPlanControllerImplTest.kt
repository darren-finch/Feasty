package com.darrenfinch.mymealplanner.screens.mealplan.controller

import com.darrenfinch.mymealplanner.TestConstants
import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.constants.DefaultModels
import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.common.helpers.SharedPreferencesHelper
import com.darrenfinch.mymealplanner.common.helpers.ToastsHelper
import com.darrenfinch.mymealplanner.common.logs.getClassTag
import com.darrenfinch.mymealplanner.common.navigation.ScreenResult
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.foods.models.presentation.UiMacroNutrients
import com.darrenfinch.mymealplanner.foods.services.MacroCalculatorService
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlan
import com.darrenfinch.mymealplanner.mealplans.usecases.*
import com.darrenfinch.mymealplanner.screens.mealplan.MealPlanVm
import com.darrenfinch.mymealplanner.screens.mealplan.view.MealPlanViewMvc
import com.darrenfinch.mymealplanner.screens.selectmealplanmeal.controller.SelectMealPlanMealFragment
import com.darrenfinch.mymealplanner.testrules.CoroutinesTestExtension
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.mockito.ArgumentMatchers.anyString

@Suppress("RemoveRedundantQualifierName")
@ExperimentalCoroutinesApi
internal class MealPlanControllerImplTest {
    private val defUiMeal = TestDefModels.defUiMeal
    private val defUiMealPlan = TestDefModels.defUiMealPlan.copy(title = "defUiMealPlan")
    private val defUiMealPlan2 = TestDefModels.defUiMealPlan.copy(title = "defUiMealPlan2")

    private val defUiMealPlanMeal = TestDefModels.defUiMealPlanMeal

    private val getAllMealPlansResult = listOf(defUiMealPlan)
    private val getMealsForMealPlanResult = listOf(defUiMealPlanMeal)

    private val defSelectedMealPlanIndex = TestConstants.DEFAULT_INDEX
    private val defSelectedMealPlanId = TestConstants.VALID_ID

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    private val viewModel = mockk<MealPlanVm>(relaxUnitFun = true)
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

    private val viewMvc = mockk<MealPlanViewMvc>(relaxUnitFun = true)

    private lateinit var SUT: MealPlanControllerImpl

    @BeforeEach
    internal fun setUp() {
        SUT = MealPlanControllerImpl(
            viewModel,
            getAllMealPlansUseCase,
            getMealsForMealPlanUseCase,
            insertMealPlanMealUseCase,
            deleteMealPlanUseCase,
            deleteMealPlanMealUseCase,
            screensNavigator,
            toastsHelper,
            sharedPreferencesHelper,
            coroutinesTestExtension.testDispatcher,
            coroutinesTestExtension.testDispatcher
        )

        SUT.bindView(viewMvc)

        every { sharedPreferencesHelper.getInt(MealPlanFragment.SELECTED_MEAL_PLAN_INDEX) } returns defSelectedMealPlanIndex
        every { viewModel.getSelectedMealPlanIndex() } returns defSelectedMealPlanIndex
        every { viewModel.getSelectedMealPlanId() } returns defSelectedMealPlanId
        coEvery { getAllMealPlansUseCase.getAllMealPlans() } returns getAllMealPlansResult
        coEvery { getMealsForMealPlanUseCase.getMealsForMealPlan(any()) } returns getMealsForMealPlanResult
    }

    @Test
    internal fun `onStart() registers listeners`() {
        SUT.onStart()

        verify {
            viewMvc.registerListener(SUT)
            screensNavigator.registerListener(SUT)
        }
    }

    @Test
    internal fun `onStop() unregisters listeners`() {
        SUT.onStop()

        verify {
            viewMvc.unregisterListener(SUT)
            screensNavigator.unregisterListener(SUT)
        }
    }

    @Test
    internal fun `refresh() shows then hides progress indication()`() = runBlockingTest {
        SUT.refresh()

        verifyOrder {
            viewMvc.showProgressIndication()
            viewMvc.hideProgressIndication()
        }
    }

    @Test
    internal fun `refresh() hides empty list indication if there are meal plans to display`() =
        runBlockingTest {
            SUT.refresh()

            verify { viewMvc.hideEmptyListIndication() }
        }

    @Test
    internal fun `refresh() binds meal plans from use case to view if there are meal plans to display`() =
        runBlockingTest {
            SUT.refresh()

            verify { viewMvc.bindMealPlans(getAllMealPlansResult) }
        }

    @Test
    internal fun `refresh() refreshes meal plan meals for selected meal plan if there are meals to display`() =
        runBlockingTest {
            SUT.refresh()

            verify { viewMvc.bindMealPlanMeals(getMealsForMealPlanResult) }
        }

    @Test
    internal fun `refresh() binds meal plan macros if there are meals to display`() =
        runBlockingTest {
            val mealPlan = UiMealPlan(
                id = 0,
                title = "",
                requiredCalories = 2000,
                requiredProteins = 175,
                requiredFats = 60,
                requiredCarbs = 250
            )
            val mealPlanMeals = listOf(
                TestDefModels.defUiMealPlanMeal.copy(
                    foods = listOf(
                        TestDefModels.defUiMealFood.copy(
                            originalMacroNutrients = UiMacroNutrients(
                                800,
                                80,
                                80,
                                30
                            )
                        ),
                        TestDefModels.defUiMealFood.copy(
                            originalMacroNutrients = UiMacroNutrients(
                                800,
                                80,
                                80,
                                30
                            )
                        )
                    )
                )
            )
            val mealPlanMacros = MacroCalculatorService.calculateMealPlanMacros(mealPlan, mealPlanMeals)
            coEvery { getAllMealPlansUseCase.getAllMealPlans() } returns listOf(mealPlan)
            coEvery { getMealsForMealPlanUseCase.getMealsForMealPlan(any()) } returns mealPlanMeals

            SUT.refresh()

            verify { viewMvc.bindMealPlanMacros(mealPlanMacros) }
        }

    @Test
    internal fun `refresh() sets view to loading`() = runBlockingTest {
        SUT.refresh()

        verify {
            viewMvc.showProgressIndication()
            viewMvc.hideEmptyListIndication()
        }
    }

    @Test
    internal fun `refresh() sets screen state to no data when there aren't any meal plans`() =
        runBlockingTest {
            coEvery { getAllMealPlansUseCase.getAllMealPlans() } returns emptyList()
            coEvery { getMealsForMealPlanUseCase.getMealsForMealPlan(any()) } returns emptyList()

            SUT.refresh()

            verifyOrder {
                viewModel.setInitialMealPlans(emptyList())
                viewMvc.bindMealPlans(emptyList())
                viewMvc.bindMealPlanMeals(emptyList())
                viewMvc.bindMealPlanMacros(DefaultModels.defaultMealPlanMacros)
                viewMvc.hideProgressIndication()
                viewMvc.showEmptyListIndication()
            }
        }

    @Test
    internal fun `refresh() sets view to have all data when there is a meal plan and meal plan meals`() =
        runBlockingTest {
            SUT.refresh()

            verifyOrder {
                viewMvc.hideProgressIndication()
                viewMvc.hideEmptyListIndication()
                viewMvc.bindMealPlans(getAllMealPlansResult)
                viewModel.setInitialMealPlans(getAllMealPlansResult)
                viewMvc.bindMealPlanMeals(getMealsForMealPlanResult)
                viewMvc.bindMealPlanMacros(
                    MacroCalculatorService.calculateMealPlanMacros(
                        getAllMealPlansResult[defSelectedMealPlanIndex],
                        getMealsForMealPlanResult
                    )
                )
                viewModel.setSelectedMealPlanIndex(defSelectedMealPlanIndex)
                viewMvc.setSelectedMealPlanIndexWithoutNotifying(defSelectedMealPlanIndex)
            }
        }

    @Test
    internal fun `refresh() sets view to show empty list indication when there is no meals for selected meal plan`() =
        runBlockingTest {
            coEvery { getMealsForMealPlanUseCase.getMealsForMealPlan(any()) } returns emptyList()

            SUT.refresh()

            verify { viewMvc.showEmptyListIndication() }
        }

    @Test
    internal fun `onMealPlanSelected() selects new meal and refreshes meals`() = runBlockingTest {
        SUT.onMealPlanSelected(defSelectedMealPlanIndex)

        coVerifyOrder {
            sharedPreferencesHelper.putInt(any(), defSelectedMealPlanIndex)
            viewModel.setSelectedMealPlanIndex(defSelectedMealPlanIndex)
            getAllMealPlansUseCase.getAllMealPlans()
        }
    }

    @Test
    internal fun `onDeleteMealPlanClicked() sets screen state to loading, moves selected meal plan index, and refreshes meal plans`() =
        runBlockingTest {
            SUT.onDeleteMealPlanClicked()

            coVerifyOrder {
                viewMvc.showProgressIndication()
                viewMvc.hideEmptyListIndication()
                viewModel.moveSelectedMealPlanIndex()
                getAllMealPlansUseCase.getAllMealPlans()
            }
        }

    @Test
    internal fun `onDeleteMealPlanMealClicked() deletes meal plan meal and refreshes meal plans`() =
        runBlockingTest {
            val defMealPlanMealId = 0
            SUT.onDeleteMealPlanMealClicked(defMealPlanMealId)

            coVerifyOrder {
                deleteMealPlanMealUseCase.deleteMealPlanMeal(defMealPlanMealId)
                getAllMealPlansUseCase.getAllMealPlans()
            }
        }

    @Test
    internal fun `onAddNewMealPlanMealClicked() shows error toast if no selected meal plan`() =
        runBlockingTest {
            every { viewModel.getSelectedMealPlanId() } returns Constants.INVALID_ID

            SUT.onAddNewMealPlanMealClicked()

            verify { toastsHelper.showShortMsg(anyString()) }
        }

    @Test
    internal fun `onAddNewMealPlanMealClicked() opens select meal plan meal fragment if there is a meal plan to add it to`() =
        runBlockingTest {
            SUT.onAddNewMealPlanMealClicked()

            verify { screensNavigator.toSelectMealPlanMealScreen() }
        }

    @Test
    internal fun `onAddNewMealPlanClicked() navigates to meal form screen`() =
        runBlockingTest {
            SUT.onAddNewMealPlanClicked()

            verify { screensNavigator.toMealPlanFormScreen() }
        }

    @Test
    internal fun `onGoBackWithResult() adds selected meal to currently selected meal plan, then refreshes meal plans`() =
        runBlockingTest {
            val screenResult = mockk<ScreenResult>(relaxUnitFun = true)
            every { screenResult.tag } returns SelectMealPlanMealFragment.getClassTag()
            every { screenResult.getSerializable(SelectMealPlanMealFragment.SELECTED_MEAL_RESULT) } returns defUiMeal

            val mealToBeAdded = defUiMealPlanMeal.copy(
                id = Constants.EXISTING_ITEM_ID,
                mealPlanId = defUiMealPlan.id,
                mealId = 0
            )

            SUT.onGoBackWithResult(screenResult)

            coVerifySequence {
                insertMealPlanMealUseCase.insertMealPlanMeal(mealToBeAdded)
                getAllMealPlansUseCase.getAllMealPlans()
            }
        }
}