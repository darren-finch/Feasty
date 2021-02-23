package com.darrenfinch.mymealplanner.screens.allmeals.controller

import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.common.dialogs.prompt.PromptDialogEvent
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.meals.usecases.DeleteMealUseCase
import com.darrenfinch.mymealplanner.meals.usecases.GetAllMealsUseCase
import com.darrenfinch.mymealplanner.screens.allmeals.AllMealsSavableData
import com.darrenfinch.mymealplanner.screens.allmeals.view.AllMealsViewMvc
import com.darrenfinch.mymealplanner.testrules.CoroutinesTestExtension
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@ExperimentalCoroutinesApi
internal class AllMealsControllerTest {
    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    private val savableData = mockk<AllMealsSavableData>(relaxUnitFun = true)
    private val screensNavigator = mockk<ScreensNavigator>(relaxUnitFun = true)
    private val getAllMealsUseCase = mockk<GetAllMealsUseCase>(relaxUnitFun = true)
    private val dialogsManager = mockk<DialogsManager>(relaxUnitFun = true)
    private val dialogsEventBus = mockk<DialogsEventBus>(relaxUnitFun = true)
    private val deleteMealUseCase = mockk<DeleteMealUseCase>(relaxUnitFun = true)

    private val viewMvc = mockk<AllMealsViewMvc>(relaxUnitFun = true)

    private lateinit var SUT: AllMealsController

    @BeforeEach
    fun setUp() {
        SUT = AllMealsController(
            savableData,
            screensNavigator,
            getAllMealsUseCase,
            deleteMealUseCase,
            dialogsManager,
            dialogsEventBus,
            coroutinesTestExtension.testDispatcher,
            coroutinesTestExtension.testDispatcher
        )
        SUT.bindView(viewMvc)
    }

    @Test
    internal fun `onStart() - registers listeners`() {
        SUT.onStart()

        verify {
            viewMvc.registerListener(SUT)
            dialogsEventBus.registerListener(SUT)
        }
    }

    @Test
    internal fun `onStop() - unregisters listeners`() {
        SUT.onStop()

        verify {
            viewMvc.unregisterListener(SUT)
            dialogsEventBus.unregisterListener(SUT)
        }
    }

    @Test
    fun `getAllMealsAndBindToView() gets all meals using use case and binds them to view`() = runBlockingTest {
        val useCaseResult = listOf(TestDefModels.defUiMeal)
        coEvery { getAllMealsUseCase.getAllMeals() } returns useCaseResult

        SUT.getAllMealsAndBindToView()

        coVerify { getAllMealsUseCase.getAllMeals() }
        coVerify { viewMvc.bindMeals(useCaseResult) }
    }

    @Test
    fun `getAllMealsAndBindToView() shows progress indication then hides it`() = runBlockingTest {
        setupGetAllMealsUseCaseResult()
        excludeRecords { viewMvc.bindMeals(any()) }

        SUT.getAllMealsAndBindToView()

        verifySequence {
            viewMvc.showProgressIndication()
            viewMvc.hideProgressIndication()
        }
    }

    @Test
    fun `onAddNewMealClicked() goes to meal form screen with new item id`() {
        SUT.onAddNewMealClicked()

        verify { screensNavigator.toMealFormScreen(Constants.NEW_ITEM_ID) }
    }

    @Test
    fun `onMealDelete() - shows prompt and saves meal id to savable data`() = runBlockingTest {
        val mealId = 1

        SUT.onMealDelete(mealId)

        verify {
            savableData.setPendingIdForMealToDelete(mealId)
            dialogsManager.showDeleteMealConfirmationDialog()
        }
    }

    @Test
    internal fun `onDialogEvent() - positive button clicked - deletes meal with pending food id, invalidates pending meal id, and refreshes meals`() = runBlockingTest {
        val mealId = 1
        every { savableData.getPendingIdForMealToDelete() } returns mealId
        setupGetAllMealsUseCaseResult()
        SUT.onDialogEvent(PromptDialogEvent.PositiveButtonClicked)

        coVerify {
            deleteMealUseCase.deleteMeal(mealId)
            savableData.setPendingIdForMealToDelete(-2)
            getAllMealsUseCase.getAllMeals()
        }
    }

    @Test
    internal fun `onDialogEvent() - negative button clicked - does nothing`() = runBlockingTest {
        SUT.onDialogEvent(PromptDialogEvent.NegativeButtonClicked)

        verify {
            listOf(deleteMealUseCase, savableData, getAllMealsUseCase) wasNot Called
        }
    }

    @Test
    fun `onMealEdit() opens meal form screen with given id`() {
        val mealId = Constants.EXISTING_ITEM_ID

        SUT.onMealEdit(mealId)

        verify { screensNavigator.toMealFormScreen(mealId) }
    }

    fun setupGetAllMealsUseCaseResult() {
        val useCaseResult = listOf(TestDefModels.defUiMeal)
        coEvery { getAllMealsUseCase.getAllMeals() } returns useCaseResult
    }
}