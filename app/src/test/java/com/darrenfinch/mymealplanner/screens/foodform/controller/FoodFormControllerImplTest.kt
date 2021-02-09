package com.darrenfinch.mymealplanner.screens.foodform.controller

import com.darrenfinch.mymealplanner.TestConstants
import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.common.navigation.BackPressDispatcher
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.foods.usecases.GetFoodUseCase
import com.darrenfinch.mymealplanner.foods.usecases.UpsertFoodUseCase
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

    val defUiFood = TestDefModels.defUiFood.copy(title = "defUiFood")
    val defUiFood2 = TestDefModels.defUiFood.copy(title = "defUiFood2")
    val getFoodUseCaseResult = TestDefModels.defUiFood.copy(title = "getFoodUseCaseResult")

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    private val viewModel = mockk<FoodFormVm>(relaxUnitFun = true)
    private val screensNavigator = mockk<ScreensNavigator>(relaxUnitFun = true)
    private val getFoodUseCase = mockk<GetFoodUseCase>(relaxUnitFun = true)
    private val upsertFoodUseCase = mockk<UpsertFoodUseCase>(relaxUnitFun = true)
    private val backPressDispatcher = mockk<BackPressDispatcher>(relaxUnitFun = true)

    private val viewMvc = mockk<FoodFormViewMvc>(relaxUnitFun = true)

    private lateinit var SUT: FoodFormControllerImpl

    @BeforeEach
    fun setUp() {
        SUT = FoodFormControllerImpl(
            viewModel,
            screensNavigator,
            getFoodUseCase,
            upsertFoodUseCase,
            backPressDispatcher,
            coroutinesTestExtension.testDispatcher,
            coroutinesTestExtension.testDispatcher
        )
        SUT.bindView(viewMvc)

        every { viewModel.getFoodDetails() } returns defUiFood
        coEvery { getFoodUseCase.getFood(any()) } returns getFoodUseCaseResult
        every { screensNavigator.navigateUp() } returns true
    }

    @Test
    fun `getFoodDetails() sets view state from view model when food details have been loaded`() {
        every { viewModel.getFoodDetails() } returns defUiFood

        SUT.getFoodDetails()

        verify {
            viewModel.bindFoodDetails(getFoodUseCaseResult)
            viewMvc.bindFoodDetails(getFoodUseCaseResult)
        }

        SUT.getFoodDetails()

        verify {
            viewModel.bindFoodDetails(defUiFood)
            viewModel.bindFoodDetails(defUiFood)
            viewMvc.bindFoodDetails(defUiFood)
        }
    }

    @Test
    fun `getFoodDetails() sets view state from use case when food details haven't been loaded`() {
        SUT.getFoodDetails()

        verify { viewModel.bindFoodDetails(getFoodUseCaseResult) }
        verify { viewModel.bindFoodDetails(getFoodUseCaseResult) }
        verify { viewMvc.bindFoodDetails(getFoodUseCaseResult) }
    }

    @Test
    fun `getFoodDetails() correctly shows then hides progress indication`() =
        runBlockingTest {
            coEvery { getFoodUseCase.getFood(any()) } returns getFoodUseCaseResult

            SUT.getFoodDetails()

            coVerifyOrder {
                viewMvc.showProgressIndication()
                getFoodUseCase.getFood(any())
                viewMvc.hideProgressIndication()
            }
        }

    @Test
    fun `onDoneButtonClicked() upserts food details then navigates up`() = runBlockingTest {
        every { viewModel.getFoodDetails() } returns defUiFood2

        SUT.onDoneButtonClicked()

        coVerifySequence {
            upsertFoodUseCase.upsertFood(defUiFood2)
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
}