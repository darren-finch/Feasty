package com.darrenfinch.mymealplanner.screens.mealform.controller

import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.common.dialogs.editmealfood.EditMealFoodDialogEvent
import com.darrenfinch.mymealplanner.common.logs.getClassTag
import com.darrenfinch.mymealplanner.common.navigation.BackPressDispatcher
import com.darrenfinch.mymealplanner.common.navigation.ScreenResult
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.meals.usecases.GetMealUseCase
import com.darrenfinch.mymealplanner.meals.usecases.InsertMealUseCase
import com.darrenfinch.mymealplanner.meals.usecases.UpdateMealUseCase
import com.darrenfinch.mymealplanner.meals.usecases.UpsertMealUseCase
import com.darrenfinch.mymealplanner.screens.mealform.MealFormVm
import com.darrenfinch.mymealplanner.screens.mealform.view.MealFormViewMvc
import com.darrenfinch.mymealplanner.screens.selectfoodformeal.controller.SelectFoodForMealFragment
import com.darrenfinch.mymealplanner.testrules.CoroutinesTestExtension
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@Suppress("RemoveRedundantQualifierName")
@ExperimentalCoroutinesApi
internal class MealFormControllerImplTest {
    val defUiMeal = TestDefModels.defUiMeal.copy(title = "defUiMeal")
    val defUiMeal2 = TestDefModels.defUiMeal.copy(title = "defUiMeal2")
    val getMealUseCaseResult = TestDefModels.defUiMeal.copy(title = "getMealUseCaseResult")

    val defUiMealFood = TestDefModels.defUiMealFood.copy(title = "defUiMealFood")

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    private val viewModel = mockk<MealFormVm>(relaxUnitFun = true)
    private val screensNavigator = mockk<ScreensNavigator>(relaxUnitFun = true)
    private val dialogsManager = mockk<DialogsManager>(relaxUnitFun = true)
    private val dialogsEventBus = mockk<DialogsEventBus>(relaxUnitFun = true)
    private val getMealUseCase = mockk<GetMealUseCase>(relaxUnitFun = true)
    private val upsertMealUseCase = mockk<UpsertMealUseCase>(relaxUnitFun = true)
    private val backPressDispatcher = mockk<BackPressDispatcher>(relaxUnitFun = true)

    private val viewMvc = mockk<MealFormViewMvc>(relaxUnitFun = true)

    private lateinit var SUT: MealFormControllerImpl

    @BeforeEach
    fun setUp() {
        SUT = MealFormControllerImpl(
            viewModel,
            upsertMealUseCase,
            getMealUseCase,
            screensNavigator,
            dialogsManager,
            dialogsEventBus,
            backPressDispatcher,
            coroutinesTestExtension.testDispatcher,
            coroutinesTestExtension.testDispatcher
        )
        SUT.bindView(viewMvc)

        // Default to editing a meal
        SUT.setArgs(Constants.EXISTING_ITEM_ID)

        every { viewModel.getMealDetails() } returns defUiMeal
        coEvery { getMealUseCase.getMeal(any()) } returns getMealUseCaseResult
        every { screensNavigator.navigateUp() } returns true
    }

    @Test
    fun `getMealDetails() sets view state from use case when meal details haven't been loaded`() {
        SUT.getMealDetails()

        verify { viewModel.bindMealDetails(getMealUseCaseResult) }
        verify { viewModel.bindMealDetails(getMealUseCaseResult) }
        verify { viewMvc.bindMealDetails(getMealUseCaseResult) }
    }

    @Test
    fun `getMealDetails() sets view state from view model when meal details have been loaded`() {
        every { viewModel.getMealDetails() } returns defUiMeal

        SUT.getMealDetails()

        verify {
            viewModel.bindMealDetails(getMealUseCaseResult)
            viewMvc.bindMealDetails(getMealUseCaseResult)
        }

        SUT.getMealDetails()

        verify {
            viewModel.bindMealDetails(defUiMeal)
            viewModel.bindMealDetails(defUiMeal)
            viewMvc.bindMealDetails(defUiMeal)
        }
    }

    @Test
    fun `getMealDetails() correctly shows then hides progress indication`() =
        runBlockingTest {
            coEvery { getMealUseCase.getMeal(any()) } returns getMealUseCaseResult

            SUT.getMealDetails()

            coVerifyOrder {
                viewMvc.showProgressIndication()
                getMealUseCase.getMeal(any())
                viewMvc.hideProgressIndication()
            }
        }

    @Test
    fun `onDoneButtonClicked() upserts meal then navigates up`() =
        runBlockingTest {
            every { viewModel.getMealDetails() } returns defUiMeal2

            SUT.onDoneButtonClicked()

            coVerifySequence {
                upsertMealUseCase.upsertMeal(defUiMeal2)
                screensNavigator.navigateUp()
            }
        }

    @Test
    internal fun `onMealFoodEdit() shows edit meal food dialog with correct arguments`() {
        val index = 1

        SUT.onMealFoodEdit(defUiMealFood, 1)

        verify { dialogsManager.showEditMealFoodDialog(defUiMealFood, index) }
    }

    @Test
    internal fun `onMealFoodDelete() removes meal food from view model and refreshes view`() {
        val index = 1
        every { viewModel.getMealDetails() } returns defUiMeal2

        SUT.onMealFoodDelete(index)

        verifyOrder {
            viewModel.removeMealFood(index)
            viewMvc.bindMealDetails(defUiMeal2)
        }
    }

    @Test
    fun `onNavigateUp() navigates up`() {
        SUT.onNavigateUp()

        verify { screensNavigator.navigateUp() }
    }

    @Test
    fun `onBackPressed() navigates up and returns true`() {
        assertEquals(SUT.onBackPressed(), true)

        verify { screensNavigator.navigateUp() }
    }

    @Test
    internal fun `onGoBackWithResult() adds select meal food to view model`() {
        val screenResult = mockk<ScreenResult>()
        val defUiFood = TestDefModels.defUiFood
        every { screenResult.tag } returns SelectFoodForMealFragment.getClassTag()
        every { screenResult.getSerializable(SelectFoodForMealFragment.SELECTED_FOOD_RESULT) } returns defUiFood

        SUT.onGoBackWithResult(screenResult)

        verify { viewModel.addMealFood(defUiFood) }
    }

    @Test
    internal fun `onDialogResult() updates meal food in view model and refreshes view if event is positive button clicked from edit meal food dialog`() {
        val index = 0
        val dialogEvent = EditMealFoodDialogEvent.OnPositiveButtonClicked(defUiMealFood, index)
        every { viewModel.getMealDetails() } returns defUiMeal2

        SUT.onDialogEvent(dialogEvent)

        verifyOrder {
            viewModel.updateMealFood(defUiMealFood, index)
            viewMvc.bindMealDetails(defUiMeal2)
        }
    }
}