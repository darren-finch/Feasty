package com.darrenfinch.mymealplanner.screens.mealplan.controller

import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.constants.DefaultModels
import com.darrenfinch.mymealplanner.common.helpers.SharedPreferencesHelper
import com.darrenfinch.mymealplanner.common.helpers.ToastsHelper
import com.darrenfinch.mymealplanner.common.navigation.ScreenDataReturnBuffer
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.foods.models.presentation.UiMacroNutrients
import com.darrenfinch.mymealplanner.foods.services.MacroCalculatorService
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlan
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlanMeal
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

@Suppress("RemoveRedundantQualifierName")
@ExperimentalCoroutinesApi
internal class MealPlanControllerImplTest {
    private val selectedMealPlanIndex = Constants.DEFAULT_INDEX
    private val selectedMealPlanId = Constants.EXISTING_ITEM_ID

    private val getAllMealPlansResult =
        listOf(TestDefModels.defUiMealPlan.copy(title = "defUiMealPlan"))
    private val getMealsForMealPlanResult = listOf(
        TestDefModels.defUiMealPlanMeal.copy(title = "defUiMealPlanMeal1"),
        TestDefModels.defUiMealPlanMeal.copy(title = "defUiMealPlanMeal2")
    )

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
    private val screenDataReturnBuffer = mockk<ScreenDataReturnBuffer>(relaxUnitFun = true)
    private val toastsHelper = mockk<ToastsHelper>(relaxUnitFun = true)
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
            screenDataReturnBuffer,
            toastsHelper,
            sharedPreferencesHelper,
            coroutinesTestExtension.testDispatcher,
            coroutinesTestExtension.testDispatcher
        )

        SUT.bindView(viewMvc)

        every { sharedPreferencesHelper.getInt(MealPlanFragment.SELECTED_MEAL_PLAN_INDEX) } returns selectedMealPlanIndex
        every { viewModel.getSelectedMealPlanIndex() } returns selectedMealPlanIndex
        every { viewModel.getSelectedMealPlanId() } returns selectedMealPlanId
        coEvery { getAllMealPlansUseCase.getAllMealPlans() } returns getAllMealPlansResult
        coEvery { getMealsForMealPlanUseCase.getMealsForMealPlan(any()) } returns getMealsForMealPlanResult
    }

    @Test
    internal fun `onStart() registers listeners`() {
        every { screenDataReturnBuffer.hasDataForToken(SelectMealPlanMealFragment.ASYNC_COMPLETION_TOKEN) } returns false

        SUT.onStart()

        verify {
            viewMvc.registerListener(SUT)
        }
    }

    @Test
    internal fun `onStart() adds chosen meal from screen data buffer to current meal plan if chosen meal exists, then refreshes meal plans`() {
        val chosenMeal = TestDefModels.defUiMeal.copy(title = "chosenMeal")
        val chosenMealAsMealPlanMeal = UiMealPlanMeal(
            id = Constants.NEW_ITEM_ID,
            mealPlanId = selectedMealPlanId,
            mealId = chosenMeal.id,
            title = chosenMeal.title,
            foods = chosenMeal.foods
        )
        every { screenDataReturnBuffer.hasDataForToken(SelectMealPlanMealFragment.ASYNC_COMPLETION_TOKEN) } returns true
        every { screenDataReturnBuffer.getData(SelectMealPlanMealFragment.ASYNC_COMPLETION_TOKEN) } returns chosenMeal

        SUT.onStart()

        coVerify {
            insertMealPlanMealUseCase.insertMealPlanMeal(chosenMealAsMealPlanMeal)
            getAllMealPlansUseCase.getAllMealPlans()
        }
    }

    @Test
    internal fun `onStart() doesn't add chosen meal to meal plan or refresh meal plans if chosen meal doesn't exist`() {
        every { screenDataReturnBuffer.hasDataForToken(SelectMealPlanMealFragment.ASYNC_COMPLETION_TOKEN) } returns false

        SUT.onStart()

        coVerify(inverse = true) {
            insertMealPlanMealUseCase.insertMealPlanMeal(any())
            getAllMealPlansUseCase.getAllMealPlans()
        }
    }

    @Test
    internal fun `onStop() unregisters listeners`() {
        SUT.onStop()

        verify {
            viewMvc.unregisterListener(SUT)
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
                            macroNutrients = UiMacroNutrients(
                                800,
                                80,
                                80,
                                30
                            )
                        ),
                        TestDefModels.defUiMealFood.copy(
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
            val mealPlanMacros =
                MacroCalculatorService.calculateMealPlanMacros(mealPlan, getMealsForMealPlanResult)
            coEvery { getAllMealPlansUseCase.getAllMealPlans() } returns listOf(mealPlan)
            coEvery { getMealsForMealPlanUseCase.getMealsForMealPlan(any()) } returns getMealsForMealPlanResult

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
                        getAllMealPlansResult[selectedMealPlanIndex],
                        getMealsForMealPlanResult
                    )
                )
                viewModel.setSelectedMealPlanIndex(selectedMealPlanIndex)
                viewMvc.setSelectedMealPlanIndexWithoutNotifying(selectedMealPlanIndex)
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
        SUT.onMealPlanSelected(selectedMealPlanIndex)

        coVerifyOrder {
            sharedPreferencesHelper.putInt(any(), selectedMealPlanIndex)
            viewModel.setSelectedMealPlanIndex(selectedMealPlanIndex)
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

            verify {
                toastsHelper.showShortMsg(R.string.no_meal_plan_selected_cant_add_meal)
            }
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
}