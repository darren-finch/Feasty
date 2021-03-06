package com.darrenfinch.mymealplanner.screens.mealform.controller

import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.common.dialogs.editmealfood.EditMealFoodDialogEvent
import com.darrenfinch.mymealplanner.common.helpers.KeyboardHelper
import com.darrenfinch.mymealplanner.common.helpers.ToastsHelper
import com.darrenfinch.mymealplanner.common.navigation.BackPressDispatcher
import com.darrenfinch.mymealplanner.common.navigation.ScreenDataReturnBuffer
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.common.validation.ValidationResult
import com.darrenfinch.mymealplanner.meals.usecases.GetMealUseCase
import com.darrenfinch.mymealplanner.meals.usecases.UpsertMealUseCase
import com.darrenfinch.mymealplanner.screens.mealform.MealFormData
import com.darrenfinch.mymealplanner.screens.mealform.MealFormValidator
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
    val defUiMealFood = TestDefModels.defUiMealFood.copy(title = "defUiMealFood")

    val getMealUseCaseResult = TestDefModels.defUiMeal.copy(title = "getMealUseCaseResult")

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    private val screenData = mockk<MealFormData>(relaxUnitFun = true)
    private val screensNavigator = mockk<ScreensNavigator>(relaxUnitFun = true)
    private val screenDataReturnBuffer = mockk<ScreenDataReturnBuffer>(relaxUnitFun = true)
    private val keyboardHelper = mockk<KeyboardHelper>(relaxUnitFun = true)
    private val dialogsManager = mockk<DialogsManager>(relaxUnitFun = true)
    private val dialogsEventBus = mockk<DialogsEventBus>(relaxUnitFun = true)
    private val toastsHelper = mockk<ToastsHelper>(relaxUnitFun = true)
    private val getMealUseCase = mockk<GetMealUseCase>(relaxUnitFun = true)
    private val upsertMealUseCase = mockk<UpsertMealUseCase>(relaxUnitFun = true)
    private val mealFormValidator = mockk<MealFormValidator>(relaxUnitFun = true)
    private val backPressDispatcher = mockk<BackPressDispatcher>(relaxUnitFun = true)

    private val viewMvc = mockk<MealFormViewMvc>(relaxUnitFun = true)

    private lateinit var SUT: MealFormControllerImpl

    @BeforeEach
    fun setUp() {
        SUT = MealFormControllerImpl(
            screenData,
            upsertMealUseCase,
            mealFormValidator,
            getMealUseCase,
            screensNavigator,
            screenDataReturnBuffer,
            dialogsManager,
            dialogsEventBus,
            toastsHelper,
            backPressDispatcher,
            coroutinesTestExtension.testDispatcher,
            coroutinesTestExtension.testDispatcher
        )
        SUT.bindView(viewMvc)

        // Default to editing a meal
        SUT.setArgs(Constants.EXISTING_ITEM_ID)

        every { screenData.getMealDetails() } returns defUiMeal
        coEvery { getMealUseCase.getMeal(any()) } returns getMealUseCaseResult
        every { screensNavigator.navigateUp() } returns true
    }

    @Test
    internal fun `onStart() registers listeners`() {
        every { screenDataReturnBuffer.hasDataForToken(SelectFoodForMealFragment.ASYNC_COMPLETION_TOKEN) } returns false

        SUT.onStart()

        verify {
            viewMvc.registerListener(SUT)
            dialogsEventBus.registerListener(SUT)
            mealFormValidator.registerListener(SUT)
            backPressDispatcher.registerListener(SUT)
        }
    }

    @Test
    internal fun `onStart() adds chosen food to screen data and rebinds meal to view if chosen food exists`() {
        val chosenUiFood = TestDefModels.defUiFood
        every { screenDataReturnBuffer.hasDataForToken(SelectFoodForMealFragment.ASYNC_COMPLETION_TOKEN) } returns true
        every { screenDataReturnBuffer.getData(SelectFoodForMealFragment.ASYNC_COMPLETION_TOKEN) } returns chosenUiFood
        every { screenData.getMealDetails() } returns defUiMeal

        SUT.onStart()

        verify {
            screenData.addMealFood(chosenUiFood)
            viewMvc.bindMealDetails(defUiMeal)
        }
    }

    @Test
    internal fun `onStart() doesn't add chosen food to screen data or rebind meal to view if chosen food doesn't exist`() {
        every { screenDataReturnBuffer.hasDataForToken(SelectFoodForMealFragment.ASYNC_COMPLETION_TOKEN) } returns false

        SUT.onStart()

        verify(inverse = true) {
            screenData.addMealFood(any())
            viewMvc.bindMealDetails(defUiMeal)
        }
    }

    @Test
    internal fun `onStop() unregisters listeners`() {
        SUT.onStop()

        verify {
            viewMvc.unregisterListener(SUT)
            dialogsEventBus.unregisterListener(SUT)
            mealFormValidator.unregisterListener(SUT)
            backPressDispatcher.unregisterListener(SUT)
        }
    }

    @Test
    fun `getMealDetails() sets view state from use case when meal details haven't been loaded`() {
        SUT.getMealDetails()

        verify { screenData.bindMealDetails(getMealUseCaseResult) }
        verify { screenData.bindMealDetails(getMealUseCaseResult) }
        verify { viewMvc.bindMealDetails(getMealUseCaseResult) }
    }

    @Test
    fun `getMealDetails() sets view state from screen data when meal details have been loaded`() {
        every { screenData.getMealDetails() } returns defUiMeal

        SUT.getMealDetails()

        verify {
            screenData.bindMealDetails(getMealUseCaseResult)
            viewMvc.bindMealDetails(getMealUseCaseResult)
        }

        SUT.getMealDetails()

        verify {
            screenData.bindMealDetails(defUiMeal)
            screenData.bindMealDetails(defUiMeal)
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
    fun `onDoneButtonClicked() - tests if form is valid`() =
        runBlockingTest {
            SUT.onDoneButtonClicked()

            verify {
                mealFormValidator.testIsValidAndNotify(screenData)
            }
        }

    @Test
    internal fun `onValidateForm() - validation success - upserts meal and navigates up`() {
        every { screenData.getMealDetails() } returns defUiMeal

        SUT.onValidateForm(ValidationResult.Success)

        coVerify {
            upsertMealUseCase.upsertMeal(defUiMeal)
            screensNavigator.navigateUp()
        }
    }

    @Test
    internal fun `onValidateForm() - validation failure - shows error msg`() {
        every { screenData.getMealDetails() } returns defUiMeal

        SUT.onValidateForm(ValidationResult.Failure(R.string.please_enter_title))

        coVerify {
            toastsHelper.showShortMsg(R.string.please_enter_title)
        }
    }

    @Test
    internal fun `onMealFoodEdit() shows edit meal food dialog with correct arguments`() {
        val index = 1

        SUT.onMealFoodEdit(defUiMealFood, 1)

        verify { dialogsManager.showEditMealFoodDialog(defUiMealFood, index) }
    }

    @Test
    internal fun `onMealFoodDelete() removes meal food from screen data and refreshes view`() {
        val index = 1
        every { screenData.getMealDetails() } returns defUiMeal

        SUT.onMealFoodDelete(index)

        verifyOrder {
            screenData.removeMealFood(index)
            viewMvc.bindMealDetails(defUiMeal)
        }
    }

    @Test
    internal fun `onAddNewFoodButtonClicked() closes keyboard and navigates to select food screen`() {
        SUT.onAddNewFoodButtonClicked()

        verify {
            screensNavigator.toSelectFoodForMealScreen()
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
    internal fun `onDialogResult() updates meal food in screen data and refreshes view if event is positive button clicked from edit meal food dialog`() {
        val index = 0
        val dialogEvent = EditMealFoodDialogEvent.OnPositiveButtonClicked(defUiMealFood, index)
        every { screenData.getMealDetails() } returns defUiMeal

        SUT.onDialogEvent(dialogEvent)

        verifyOrder {
            screenData.updateMealFood(defUiMealFood, index)
            viewMvc.bindMealDetails(defUiMeal)
        }
    }
}