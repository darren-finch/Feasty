package com.darrenfinch.mymealplanner.screens.foodform.controller

import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.common.helpers.ToastsHelper
import com.darrenfinch.mymealplanner.common.navigation.BackPressDispatcher
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.common.validation.ValidationResult
import com.darrenfinch.mymealplanner.foods.usecases.GetFoodUseCase
import com.darrenfinch.mymealplanner.foods.usecases.UpsertFoodUseCase
import com.darrenfinch.mymealplanner.screens.foodform.FoodFormData
import com.darrenfinch.mymealplanner.screens.foodform.FoodFormValidator
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
    val getFoodUseCaseResult = TestDefModels.defUiFood.copy(title = "getFoodUseCaseResult")

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    private val screenData = mockk<FoodFormData>(relaxUnitFun = true)
    private val screensNavigator = mockk<ScreensNavigator>(relaxUnitFun = true)
    private val getFoodUseCase = mockk<GetFoodUseCase>(relaxUnitFun = true)
    private val upsertFoodUseCase = mockk<UpsertFoodUseCase>(relaxUnitFun = true)
    private val foodFormValidator = mockk<FoodFormValidator>(relaxUnitFun = true)
    private val toastsHelper = mockk<ToastsHelper>(relaxUnitFun = true)
    private val backPressDispatcher = mockk<BackPressDispatcher>(relaxUnitFun = true)

    private val viewMvc = mockk<FoodFormViewMvc>(relaxUnitFun = true)

    private lateinit var SUT: FoodFormControllerImpl

    @BeforeEach
    fun setUp() {
        SUT = FoodFormControllerImpl(
            screenData,
            screensNavigator,
            getFoodUseCase,
            upsertFoodUseCase,
            foodFormValidator,
            toastsHelper,
            backPressDispatcher,
            coroutinesTestExtension.testDispatcher,
            coroutinesTestExtension.testDispatcher
        )
        SUT.bindView(viewMvc)

        every { screenData.getFoodDetails() } returns defUiFood
        coEvery { getFoodUseCase.getFood(any()) } returns getFoodUseCaseResult
        every { screensNavigator.navigateUp() } returns true
    }

    @Test
    internal fun `onStart() registers listeners`() {
        SUT.onStart()

        verify {
            viewMvc.registerListener(SUT)
            backPressDispatcher.registerListener(SUT)
            foodFormValidator.registerListener(SUT)
        }
    }

    @Test
    internal fun `onStop() unregisters listeners`() {
        SUT.onStop()

        verify {
            viewMvc.unregisterListener(SUT)
            backPressDispatcher.unregisterListener(SUT)
            foodFormValidator.unregisterListener(SUT)
        }
    }

    @Test
    fun `getFoodDetails() sets view state from screen data when food details have been loaded`() {
        every { screenData.getFoodDetails() } returns defUiFood

        SUT.getFoodDetails()

        verify {
            screenData.bindFoodDetails(getFoodUseCaseResult)
            viewMvc.bindFoodDetails(getFoodUseCaseResult)
        }

        SUT.getFoodDetails()

        verify {
            screenData.bindFoodDetails(defUiFood)
            screenData.bindFoodDetails(defUiFood)
            viewMvc.bindFoodDetails(defUiFood)
        }
    }

    @Test
    fun `getFoodDetails() sets view state from use case when food details haven't been loaded`() {
        SUT.getFoodDetails()

        verify { screenData.bindFoodDetails(getFoodUseCaseResult) }
        verify { screenData.bindFoodDetails(getFoodUseCaseResult) }
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
    fun `onDoneButtonClicked() tests if form is valid`() = runBlockingTest {
        SUT.onDoneButtonClicked()

        verify {
            foodFormValidator.testIsValidAndNotify(screenData)
        }
    }

    @Test
    internal fun `onValidateForm() - validation success - upserts food details and navigates up`() {
        every { screenData.getFoodDetails() } returns defUiFood

        SUT.onValidateForm(ValidationResult.Success)

        coVerify {
            upsertFoodUseCase.upsertFood(defUiFood)
            screensNavigator.navigateUp()
        }
    }

    @Test
    internal fun `onValidateForm() - validation failure - shows error msg`() {
        SUT.onValidateForm(ValidationResult.Failure(R.string.please_enter_title))

        verify {
            toastsHelper.showShortMsg(R.string.please_enter_title)
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