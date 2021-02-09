package com.darrenfinch.mymealplanner.screens.mealform.controller

import com.darrenfinch.mymealplanner.TestConstants
import com.darrenfinch.mymealplanner.TestDefModels
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
    private val insertMealUseCase = mockk<InsertMealUseCase>(relaxUnitFun = true)
    private val updateMealUseCase = mockk<UpdateMealUseCase>(relaxUnitFun = true)
    private val backPressDispatcher = mockk<BackPressDispatcher>(relaxUnitFun = true)

    private val viewMvc = mockk<MealFormViewMvc>(relaxUnitFun = true)

    private lateinit var SUT: MealFormControllerImpl

    @BeforeEach
    fun setUp() {
        SUT = MealFormControllerImpl(
            viewModel,
            insertMealUseCase,
            updateMealUseCase,
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
        SUT.setArgs(TestConstants.VALID_ID)

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
    fun `onDoneButtonClicked() inserts meal from view model if adding meal, then navigates up`() =
        runBlockingTest {
            SUT.setArgs(TestConstants.INVALID_ID)
            every { viewModel.getMealDetails() } returns defUiMeal2

            SUT.onDoneButtonClicked()

            coVerifySequence {
                insertMealUseCase.insertMeal(defUiMeal2)
                screensNavigator.navigateUp()
            }
        }

    @Test
    fun `onDoneButtonClicked() updates meal from view model if editing meal, then navigates up`() =
        runBlockingTest {
            SUT.setArgs(TestConstants.VALID_ID)
            every { viewModel.getMealDetails() } returns defUiMeal2

            SUT.onDoneButtonClicked()

            coVerifySequence {
                updateMealUseCase.updateMeal(defUiMeal2)
                screensNavigator.navigateUp()
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
        val dialogEvent = EditMealFoodDialogEvent.OnPositiveButtonClicked(defUiMealFood)
        every { viewModel.getMealDetails() } returns defUiMeal2

        SUT.onDialogEvent(dialogEvent)

        verifyOrder {
            viewModel.updateMealFood(defUiMealFood)
            viewMvc.bindMealDetails(defUiMeal2)
        }
    }
}