package com.darrenfinch.mymealplanner.screens.mealform.controller

import com.darrenfinch.mymealplanner.TestConstants
import com.darrenfinch.mymealplanner.TestDefaultModels
import com.darrenfinch.mymealplanner.common.dialogs.DialogResult
import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodformeal.SelectFoodForMealDialogEvent
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodformeal.controller.SelectFoodForMealDialog
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.SelectFoodQuantityDialogEvent
import com.darrenfinch.mymealplanner.common.dialogs.selectfoodquantity.controller.SelectFoodQuantityDialog
import com.darrenfinch.mymealplanner.common.navigation.BackPressDispatcher
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.meals.usecases.GetMealUseCase
import com.darrenfinch.mymealplanner.meals.usecases.InsertMealUseCase
import com.darrenfinch.mymealplanner.meals.usecases.UpdateMealUseCase
import com.darrenfinch.mymealplanner.screens.mealform.MealFormVm
import com.darrenfinch.mymealplanner.screens.mealform.view.MealFormViewMvc
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
    val defUiMeal = TestDefaultModels.defUiMeal.copy(title = "defUiMeal")
    val defUiMeal2 = TestDefaultModels.defUiMeal.copy(title = "defUiMeal2")
    val getMealUseCaseResult = TestDefaultModels.defUiMeal.copy(title = "getMealUseCaseResult")

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
        every { screensNavigator.goBack() } returns true
    }

    @Test
    fun `getMealDetailsIfPossibleAndBindToView() restores view state from view model when meal details are dirty`() {
        setupDirtyMealDetails()
        every { viewModel.getMealDetails() } returns defUiMeal2

        SUT.getMealDetailsIfPossibleAndBindToView()

        verify { viewMvc.bindMealDetails(defUiMeal2) }
    }

    @Test
    fun `getMealDetailsIfPossibleAndBindToView() restores view state from view model when meal details are clean`() {
        setupCleanMealDetails()
        every { viewModel.getMealDetails() } returns defUiMeal2
        coEvery { getMealUseCase.getMeal(any()) } returns getMealUseCaseResult

        SUT.getMealDetailsIfPossibleAndBindToView()

        verify { viewMvc.bindMealDetails(defUiMeal2) }
    }

    @Test
    fun `getMealDetailsIfPossibleAndBindToView() gets meal details from database if editing meal and meal details are clean`() =
        runBlockingTest {
            val mealId = TestConstants.VALID_ID

            setupEditingMeal()
            setupCleanMealDetails()

            coEvery { getMealUseCase.getMeal(mealId) } returns getMealUseCaseResult

            SUT.getMealDetailsIfPossibleAndBindToView()

            coVerify { getMealUseCase.getMeal(mealId) }
        }

    @Test
    fun `getMealDetailsIfPossibleAndBindToView() doesn't get meal details from database if meal details are dirty`() {
        setupDirtyMealDetails()

        SUT.getMealDetailsIfPossibleAndBindToView()

        coVerify { getMealUseCase.getMeal(any()) wasNot Called }
    }

    @Test
    fun `getMealDetailsIfPossibleAndBindToView() doesn't get meal details from database when adding a meal and meal details are dirty`() {
        setupAddingMeal()
        setupDirtyMealDetails()

        SUT.getMealDetailsIfPossibleAndBindToView()

        coVerify { getMealUseCase.getMeal(any()) wasNot Called }
    }

    @Test
    fun `getMealDetailsIfPossibleAndBindToView() doesn't get meal details from database when adding a meal and meal details are clean`() {
        setupAddingMeal()
        setupCleanMealDetails()

        SUT.getMealDetailsIfPossibleAndBindToView()

        coVerify { getMealUseCase.getMeal(any()) wasNot Called }
    }

    @Test
    fun `getMealDetailsIfPossibleAndBindToView() binds fetched meal details to viewMvc and view model when able to get meal details`() =
        runBlockingTest {
            setupEditingMeal()
            setupCleanMealDetails()
            coEvery { getMealUseCase.getMeal(TestConstants.VALID_ID) } returns getMealUseCaseResult

            SUT.getMealDetailsIfPossibleAndBindToView()

            verify { viewMvc.bindMealDetails(getMealUseCaseResult) }
            verify { viewModel.bindInitialMealDetails(getMealUseCaseResult) }
        }

    @Test
    fun `getMealDetailsIfPossibleAndBindToView() correctly hides, shows, then hides progress indication when able to get meal details`() =
        runBlockingTest {
            setupEditingMeal()
            setupCleanMealDetails()
            excludeRecords { viewMvc.bindMealDetails(any()) }
            coEvery { getMealUseCase.getMeal(any()) } returns getMealUseCaseResult

            SUT.getMealDetailsIfPossibleAndBindToView()

            verifySequence {
                viewMvc.hideProgressIndication()
                viewMvc.showProgressIndication()
                viewMvc.hideProgressIndication()
            }
        }

    @Test
    fun `getMealDetailsIfPossibleAndBindToView() hides progress indication if unable to get meal details`() =
        runBlockingTest {
            setupDirtyMealDetails()
            excludeRecords { viewMvc.bindMealDetails(any()) }

            SUT.getMealDetailsIfPossibleAndBindToView()

            verify { viewMvc.hideProgressIndication() }
        }

    @Test
    fun `restoreViewState() restores view state from view model`() {
        every { viewModel.getMealDetails() } returns defUiMeal2

        SUT.restoreViewState()

        verify { viewMvc.bindMealDetails(defUiMeal2) }
    }

    @Test
    fun `setInitialMealDetails() binds meal details to view and view model`() {
        SUT.setInitialMealDetails(defUiMeal2)

        verify { viewMvc.bindMealDetails(defUiMeal2) }
        verify { viewModel.bindInitialMealDetails(defUiMeal2) }
    }

    @Test
    fun `onDoneButtonClicked() inserts meal from view model if adding meal, then navigates up`() =
        runBlockingTest {
            setupAddingMeal()
            every { viewModel.getMealDetails() } returns defUiMeal2

            SUT.onDoneButtonClicked()

            coVerifySequence {
                insertMealUseCase.insertMeal(defUiMeal2)
                screensNavigator.goBack()
            }
        }

    @Test
    fun `onDoneButtonClicked() updates meal from view model if editing meal, then navigates up`() =
        runBlockingTest {
            setupEditingMeal()
            every { viewModel.getMealDetails() } returns defUiMeal2

            SUT.onDoneButtonClicked()

            coVerifySequence {
                updateMealUseCase.updateMeal(defUiMeal2)
                screensNavigator.goBack()
            }
        }

    @Test
    fun `onNavigateUp() navigates up`() {
        SUT.onNavigateUp()

        verify { screensNavigator.goBack() }
    }

    @Test
    fun `onBackPressed() navigates up and returns true`() {
        assertEquals(SUT.onBackPressed(), true)

        verify { screensNavigator.goBack() }
    }

    @Test
    fun `onDialogEvent() shows select food quantity dialog with correct args when event is SelectFoodForMealDialog-ON_FOOD_CHOSEN`() {
        val foodId = TestConstants.VALID_ID
        val result = mockk<DialogResult>()
        every { result.getInt(SelectFoodForMealDialog.FOOD_ID_RESULT) } returns foodId

        SUT.onDialogEvent(SelectFoodForMealDialogEvent.ON_FOOD_CHOSEN, result)

        verify { dialogsManager.showSelectFoodQuantityDialog(foodId) }
    }

    @Test
    fun `onDialogEvent() adds selected food with desired serving size from select food quantity screen to view model and binds updated meal details to view`() {
        val selectedFood = TestDefaultModels.defUiFood
        val desiredServingSize = TestDefaultModels.defPhysicalQuantity

        val result = mockk<DialogResult>()
        every { result.getSerializable(SelectFoodQuantityDialog.SELECTED_FOOD_RESULT) } returns selectedFood
        every { result.getSerializable(SelectFoodQuantityDialog.DESIRED_SERVING_SIZE_RESULT) } returns desiredServingSize

        every { viewModel.getMealDetails() } returns defUiMeal2

        SUT.onDialogEvent(SelectFoodQuantityDialogEvent.ON_DESIRED_FOOD_SERVING_SIZE_CHOSEN, result)

        verify { viewModel.addMealFood(selectedFood, desiredServingSize) }
        verify { viewMvc.bindMealDetails(defUiMeal2) }
    }

    fun setupAddingMeal() {
        SUT.setArgs(TestConstants.INVALID_ID)
    }

    fun setupEditingMeal() {
        SUT.setArgs(TestConstants.VALID_ID)
    }

    fun setupCleanMealDetails() {
        every { viewModel.isDirty } returns false
    }

    fun setupDirtyMealDetails() {
        every { viewModel.isDirty } returns true
    }
}