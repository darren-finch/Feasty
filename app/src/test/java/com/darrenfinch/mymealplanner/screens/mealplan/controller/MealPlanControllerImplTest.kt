package com.darrenfinch.mymealplanner.screens.mealplan.controller

import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.helpers.SharedPreferencesHelper
import com.darrenfinch.mymealplanner.common.helpers.ToastsHelper
import com.darrenfinch.mymealplanner.common.navigation.ScreenDataReturnBuffer
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlanMeal
import com.darrenfinch.mymealplanner.mealplans.usecases.*
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

    private val allMealPlans =
        listOf(TestDefModels.defUiMealPlan.copy(title = "defUiMealPlan"))
    private val mealsForSelectedMealPlan = listOf(
        TestDefModels.defUiMealPlanMeal.copy(title = "defUiMealPlanMeal1"),
        TestDefModels.defUiMealPlanMeal.copy(title = "defUiMealPlanMeal2")
    )

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    private val savableScreenData = mockk<MealPlanSavableData>(relaxUnitFun = true)
    private val screenStatePresenter = mockk<MealPlanScreenStatePresenter>(relaxUnitFun = true)
    private val refreshMealPlanScreenUseCase = mockk<RefreshMealPlanScreenUseCase>()
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
            savableScreenData,
            screenStatePresenter,
            refreshMealPlanScreenUseCase,
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
        every { savableScreenData.getSelectedMealPlanIndex() } returns selectedMealPlanIndex
        every { savableScreenData.getSelectedMealPlanId() } returns selectedMealPlanId
        coEvery { refreshMealPlanScreenUseCase.refresh() } returns RefreshMealPlanScreenUseCase.Result.NoMealPlans
    }

    @Test
    internal fun `bindView() binds view to screen state presenter`() {
        SUT.bindView(viewMvc)

        verify { screenStatePresenter.bindView(viewMvc) }
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
            refreshMealPlanScreenUseCase.refresh()
        }
    }

    @Test
    internal fun `onStart() doesn't add chosen meal to meal plan or refresh meal plans if chosen meal doesn't exist`() {
        every { screenDataReturnBuffer.hasDataForToken(SelectMealPlanMealFragment.ASYNC_COMPLETION_TOKEN) } returns false

        SUT.onStart()

        coVerify(inverse = true) {
            insertMealPlanMealUseCase.insertMealPlanMeal(any())
            refreshMealPlanScreenUseCase.refresh()
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
    internal fun `refresh() sets screen state to loading, then shows screen state through presenter`() =
        runBlockingTest {
            coEvery { refreshMealPlanScreenUseCase.refresh() } returns RefreshMealPlanScreenUseCase.Result.NoMealPlans
            excludeRecords { screenStatePresenter.bindView(viewMvc) }

            SUT.refresh()

            verifyOrder {
                screenStatePresenter.presentState(MealPlanScreenState.Loading)
                screenStatePresenter.presentState(MealPlanScreenState.NoMealPlans)
            }
        }

    @Test
    internal fun `refresh() - when refresh meal plan screen use case returns no meal plans result - sets initial meal plans in screen data to empty list`() =
        runBlockingTest {
            coEvery { refreshMealPlanScreenUseCase.refresh() } returns RefreshMealPlanScreenUseCase.Result.NoMealPlans

            SUT.refresh()

            verify {
                savableScreenData.setInitialMealPlans(emptyList())
            }
        }

    @Test
    internal fun `refresh() - when refresh meal plan screen use case returns has meal plans but no meals for selected meal plan result - sets meal plans in screen data and updates selected meal plan index`() =
        runBlockingTest {
            coEvery { refreshMealPlanScreenUseCase.refresh() } returns RefreshMealPlanScreenUseCase.Result.HasMealPlansButNoMealsForSelectedMealPlan(
                allMealPlans,
                TestDefModels.defMealPlanMacros,
                selectedMealPlanIndex
            )

            SUT.refresh()

            verify {
                savableScreenData.setInitialMealPlans(allMealPlans)
                sharedPreferencesHelper.putInt(
                    MealPlanFragment.SELECTED_MEAL_PLAN_INDEX,
                    selectedMealPlanIndex
                )
                savableScreenData.setSelectedMealPlanIndex(selectedMealPlanIndex)
                viewMvc.setSelectedMealPlanIndex(selectedMealPlanIndex)
            }
        }

    @Test
    internal fun `refresh() - when refresh meal plan screen use case returns has meal plans and meals for selected meal plan result - sets meal plans in screen data and updates selected meal plan index`() =
        runBlockingTest {
            coEvery { refreshMealPlanScreenUseCase.refresh() } returns RefreshMealPlanScreenUseCase.Result.HasMealPlansAndMealsForSelectedMealPlan(
                allMealPlans,
                TestDefModels.defMealPlanMacros,
                selectedMealPlanIndex,
                mealsForSelectedMealPlan
            )

            SUT.refresh()

            verify {
                savableScreenData.setInitialMealPlans(allMealPlans)
                sharedPreferencesHelper.putInt(
                    MealPlanFragment.SELECTED_MEAL_PLAN_INDEX,
                    selectedMealPlanIndex
                )
                savableScreenData.setSelectedMealPlanIndex(selectedMealPlanIndex)
                viewMvc.setSelectedMealPlanIndex(selectedMealPlanIndex)
            }
        }

    @Test
    internal fun `onMealPlanSelectedByUser() selects new meal and refreshes meals`() =
        runBlockingTest {
            SUT.onMealPlanSelectedByUser(selectedMealPlanIndex)

            coVerifyOrder {
                sharedPreferencesHelper.putInt(any(), selectedMealPlanIndex)
                savableScreenData.setSelectedMealPlanIndex(selectedMealPlanIndex)
                refreshMealPlanScreenUseCase.refresh()
            }
        }

    @Test
    internal fun `onDeleteMealPlanClicked() moves selected meal plan index and refreshes meal plans`() =
        runBlockingTest {
            SUT.onDeleteMealPlanClicked()

            coVerifyOrder {
                savableScreenData.moveSelectedMealPlanIndex()
                refreshMealPlanScreenUseCase.refresh()
            }
        }

    @Test
    internal fun `onDeleteMealPlanMealClicked() deletes meal plan meal and refreshes meal plans`() =
        runBlockingTest {
            val defMealPlanMealId = 0
            SUT.onDeleteMealPlanMealClicked(defMealPlanMealId)

            coVerifyOrder {
                deleteMealPlanMealUseCase.deleteMealPlanMeal(defMealPlanMealId)
                refreshMealPlanScreenUseCase.refresh()
            }
        }

    @Test
    internal fun `onAddNewMealPlanMealClicked() shows error toast if no selected meal plan`() =
        runBlockingTest {
            every { savableScreenData.getSelectedMealPlanId() } returns Constants.INVALID_ID

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