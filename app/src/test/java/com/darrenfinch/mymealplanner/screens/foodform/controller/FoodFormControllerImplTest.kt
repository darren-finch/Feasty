package com.darrenfinch.mymealplanner.screens.foodform.controller

import com.darrenfinch.mymealplanner.TestConstants
import com.darrenfinch.mymealplanner.TestDefaultModels
import com.darrenfinch.mymealplanner.common.navigation.BackPressDispatcher
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.foods.usecases.GetFoodUseCase
import com.darrenfinch.mymealplanner.foods.usecases.InsertFoodUseCase
import com.darrenfinch.mymealplanner.foods.usecases.UpdateFoodUseCase
import com.darrenfinch.mymealplanner.screens.foodform.FoodFormVm
import com.darrenfinch.mymealplanner.screens.foodform.view.FoodFormViewMvc
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
internal class FoodFormControllerImplTest {

    val defUiFood = TestDefaultModels.defUiFood.copy(title = "defUiFood")
    val defUiFood2 = TestDefaultModels.defUiFood.copy(title = "defUiFood2")
    val getFoodUseCaseResult = TestDefaultModels.defUiFood.copy(title = "getFoodUseCaseResult")

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    private val viewModel = mockk<FoodFormVm>(relaxUnitFun = true)
    private val screensNavigator = mockk<ScreensNavigator>(relaxUnitFun = true)
    private val getFoodUseCase = mockk<GetFoodUseCase>(relaxUnitFun = true)
    private val insertFoodUseCase = mockk<InsertFoodUseCase>(relaxUnitFun = true)
    private val updateFoodUseCase = mockk<UpdateFoodUseCase>(relaxUnitFun = true)
    private val backPressDispatcher = mockk<BackPressDispatcher>(relaxUnitFun = true)

    private val viewMvc = mockk<FoodFormViewMvc>(relaxUnitFun = true)

    private lateinit var SUT: FoodFormControllerImpl

    @BeforeEach
    fun setUp() {
        SUT = FoodFormControllerImpl(
            viewModel,
            screensNavigator,
            getFoodUseCase,
            insertFoodUseCase,
            updateFoodUseCase,
            backPressDispatcher,
            coroutinesTestExtension.testDispatcher,
            coroutinesTestExtension.testDispatcher
        )
        SUT.bindView(viewMvc)

        // Default to editing a meal
        SUT.setArgs(TestConstants.VALID_ID)

        every { viewModel.getFoodDetails() } returns defUiFood
        every { screensNavigator.goBack() } returns true
    }

    @Test
    fun `getFoodDetailsIfPossibleAndBindToView() restores view state when food details are dirty`() {
        setupDirtyFoodDetails()

        SUT.getFoodDetailsIfPossibleAndBindToView()

        verify { viewMvc.bindFoodDetails(defUiFood) }
    }

    @Test
    fun `getFoodDetailsIfPossibleAndBindToView() restores view state when food details are clean`() {
        setupCleanFoodDetails()
        coEvery { getFoodUseCase.getFood(any()) } returns getFoodUseCaseResult

        SUT.getFoodDetailsIfPossibleAndBindToView()

        verify { viewMvc.bindFoodDetails(defUiFood) }
    }

    @Test
    fun `getFoodDetailsIfPossibleAndBindToView() gets food details from database if editing food and food details are clean`() = runBlockingTest {
        val foodId = TestConstants.VALID_ID

        setupEditingFood()
        setupCleanFoodDetails()

        coEvery { getFoodUseCase.getFood(foodId) } returns getFoodUseCaseResult

        SUT.getFoodDetailsIfPossibleAndBindToView()

        coVerify { getFoodUseCase.getFood(foodId) }
    }

    @Test
    fun `getFoodDetailsIfPossibleAndBindToView() doesn't get food details from database if food details are dirty`() {
        setupDirtyFoodDetails()

        SUT.getFoodDetailsIfPossibleAndBindToView()

        coVerify { getFoodUseCase.getFood(any()) wasNot Called }
    }

    @Test
    fun `getFoodDetailsIfPossibleAndBindToView() doesn't get food details from database when adding a food and food details are dirty`() {
        setupAddingFood()
        setupDirtyFoodDetails()

        SUT.getFoodDetailsIfPossibleAndBindToView()

        coVerify { getFoodUseCase.getFood(any()) wasNot Called }
    }

    @Test
    fun `getFoodDetailsIfPossibleAndBindToView() doesn't get food details from database when adding a food and food details are clean`() {
        setupAddingFood()
        setupCleanFoodDetails()

        SUT.getFoodDetailsIfPossibleAndBindToView()

        coVerify { getFoodUseCase.getFood(any()) wasNot Called }
    }

    @Test
    fun `getFoodDetailsIfPossibleAndBindToView() binds fetched food details to viewMvc and view model when able to get food details`() = runBlockingTest {
        setupEditingFood()
        setupCleanFoodDetails()
        coEvery { getFoodUseCase.getFood(TestConstants.VALID_ID) } returns getFoodUseCaseResult

        SUT.getFoodDetailsIfPossibleAndBindToView()

        verify { viewMvc.bindFoodDetails(getFoodUseCaseResult) }
        verify { viewModel.bindInitialFoodDetails(getFoodUseCaseResult) }
    }

    @Test
    fun `getFoodDetailsIfPossibleAndBindToView() correctly hides, shows, then hides progress indication when able to get food details`() = runBlockingTest {
        setupEditingFood()
        setupCleanFoodDetails()
        excludeRecords { viewMvc.bindFoodDetails(any()) }
        coEvery { getFoodUseCase.getFood(any()) } returns getFoodUseCaseResult

        SUT.getFoodDetailsIfPossibleAndBindToView()

        verifySequence {
            viewMvc.hideProgressIndication()
            viewMvc.showProgressIndication()
            viewMvc.hideProgressIndication()
        }
    }

    @Test
    fun `getFoodDetailsIfPossibleAndBindToView() hides progress indication if unable to get food details`() = runBlockingTest {
        setupDirtyFoodDetails()
        excludeRecords { viewMvc.bindFoodDetails(any()) }

        SUT.getFoodDetailsIfPossibleAndBindToView()

        verify { viewMvc.hideProgressIndication() }
    }

    @Test
    fun `restoreViewState() restores view state from view model`() {
        every { viewModel.getFoodDetails() } returns defUiFood2

        SUT.restoreViewState()

        verify { viewMvc.bindFoodDetails(defUiFood2) }
    }

    @Test
    fun `setInitialFoodDetails() binds food details to view and view model`() {
        SUT.setInitialFoodDetails(defUiFood2)

        verify { viewMvc.bindFoodDetails(defUiFood2) }
        verify { viewModel.bindInitialFoodDetails(defUiFood2) }
    }

    @Test
    fun `onDoneButtonClicked() inserts food from view model if adding food, then navigates up`() = runBlockingTest {
        setupAddingFood()
        every { viewModel.getFoodDetails() } returns defUiFood2

        SUT.onDoneButtonClicked()

        coVerifySequence {
            insertFoodUseCase.insertFood(defUiFood2)
            screensNavigator.goBack()
        }
    }

    @Test
    fun `onDoneButtonClicked() updates food from view model if editing food, then navigates up`() = runBlockingTest {
        setupEditingFood()
        every { viewModel.getFoodDetails() } returns defUiFood2

        SUT.onDoneButtonClicked()

        coVerifySequence {
            updateFoodUseCase.updateFood(defUiFood2)
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

    fun setupAddingFood() {
        SUT.setArgs(TestConstants.INVALID_ID)
    }

    fun setupEditingFood() {
        SUT.setArgs(TestConstants.VALID_ID)
    }

    fun setupCleanFoodDetails() {
        every { viewModel.isDirty } returns false
    }

    fun setupDirtyFoodDetails() {
        every { viewModel.isDirty } returns true
    }
}