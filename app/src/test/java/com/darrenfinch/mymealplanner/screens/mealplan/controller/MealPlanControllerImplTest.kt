package com.darrenfinch.mymealplanner.screens.mealplan.controller

import com.darrenfinch.mymealplanner.TestConstants
import com.darrenfinch.mymealplanner.TestDefaultModels
import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.constants.DefaultModels
import com.darrenfinch.mymealplanner.common.dialogs.DialogResult
import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.screens.selectmealplanmeal.SelectMealPlanMealDialogEvent
import com.darrenfinch.mymealplanner.screens.selectmealplanmeal.controller.SelectMealPlanMealDialog
import com.darrenfinch.mymealplanner.common.helpers.SharedPreferencesHelper
import com.darrenfinch.mymealplanner.common.helpers.ToastsHelper
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.foods.models.domain.MacroCalculator
import com.darrenfinch.mymealplanner.foods.models.presentation.UiMacroNutrients
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlan
import com.darrenfinch.mymealplanner.mealplans.usecases.*
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
    private val defUiMeal = TestDefaultModels.defUiMeal
    private val defUiMealPlan = TestDefaultModels.defUiMealPlan.copy(title = "defUiMealPlan")
    private val defUiMealPlan2 = TestDefaultModels.defUiMealPlan.copy(title = "defUiMealPlan2")

    private val defUiMealPlanMeal = TestDefaultModels.defUiMealPlanMeal

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
        coEvery { getMealsForMealPlanUseCase.getMealsForMealPlan(any()) } returns getMealsForMealPlanResult
    }

    @Test
    internal fun `onStart() registers listeners`() {
        SUT.onStart()

        verify {
            viewMvc.registerListener(SUT)
            dialogsEventBus.registerListener(SUT)
        }
    }

    @Test
    internal fun `onStop() unregisters listeners`() {
        SUT.onStop()

        verify {
            viewMvc.unregisterListener(SUT)
            dialogsEventBus.unregisterListener(SUT)
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
                    MacroCalculator.calculateMealPlanMacros(
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

            verify { toastsHelper.showShortMsg(any()) }
        }

    @Test
    internal fun `onAddNewMealPlanMealClicked() opens select meal plan meal dialog if there is a meal plan to add it to`() =
        runBlockingTest {
            SUT.onAddNewMealPlanMealClicked()

            verify { dialogsManager.showSelectMealPlanMealDialog() }
        }

    @Test
    internal fun `onAddNewMealPlanClicked() navigates to meal form screen`() =
        runBlockingTest {
            SUT.onAddNewMealPlanClicked()

            verify { screensNavigator.toMealPlanFormScreen() }
        }

    @Test
    internal fun `onDialogEvent() adds selected meal to currently selected meal plan, then refreshes meal plans`() =
        runBlockingTest {
            val dialogResult = mockk<DialogResult>(relaxUnitFun = true)
            every { dialogResult.getSerializable(SelectMealPlanMealDialog.SELECTED_MEAL_RESULT) } returns defUiMeal

            val mealToBeAdded = defUiMealPlanMeal.copy(
                id = Constants.VALID_ID,
                mealPlanId = defUiMealPlan.id,
                mealId = 0
            )

            SUT.onDialogEvent(
                SelectMealPlanMealDialogEvent.ON_MEAL_SELECTED,
                dialogResult
            )

            coVerifySequence {
                insertMealPlanMealUseCase.insertMealPlanMeal(mealToBeAdded)
                getAllMealPlansUseCase.getAllMealPlans()
            }
        }
}