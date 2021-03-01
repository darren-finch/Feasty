package com.darrenfinch.mymealplanner.screens.allfoods.controller

import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.common.constants.Constants
import com.darrenfinch.mymealplanner.common.dialogs.DialogsEventBus
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.common.dialogs.prompt.PromptDialogEvent
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.foods.usecases.DeleteFoodUseCase
import com.darrenfinch.mymealplanner.foods.usecases.GetAllFoodsUseCase
import com.darrenfinch.mymealplanner.screens.allfoods.AllFoodsSavableData
import com.darrenfinch.mymealplanner.screens.allfoods.view.AllFoodsViewMvc
import com.darrenfinch.mymealplanner.testrules.CoroutinesTestExtension
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@ExperimentalCoroutinesApi
internal class AllFoodsControllerTest {

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    private val screensNavigator = mockk<ScreensNavigator>(relaxUnitFun = true)
    private val getAllFoodsUseCase = mockk<GetAllFoodsUseCase>(relaxUnitFun = true)
    private val savableData = mockk<AllFoodsSavableData>(relaxUnitFun = true)
    private val dialogsManager = mockk<DialogsManager>(relaxUnitFun = true)
    private val dialogsEventBus = mockk<DialogsEventBus>(relaxUnitFun = true)
    private val deleteFoodUseCase = mockk<DeleteFoodUseCase>(relaxUnitFun = true)

    private val viewMvc = mockk<AllFoodsViewMvc>(relaxUnitFun = true)

    private lateinit var SUT: AllFoodsController

    @BeforeEach
    fun setUp() {
        SUT = AllFoodsController(
            savableData,
            screensNavigator,
            getAllFoodsUseCase,
            deleteFoodUseCase,
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
    fun `getAllFoodsAndBindToView() gets all foods using use case and binds them to view`() =
        runBlockingTest {
            val useCaseResult = listOf(TestDefModels.defUiFood)
            coEvery { getAllFoodsUseCase.getAllFoods() } returns useCaseResult

            SUT.getAllFoodsAndBindToView()

            coVerify { getAllFoodsUseCase.getAllFoods() }
            coVerify { viewMvc.bindFoods(useCaseResult) }
        }

    @Test
    fun `getAllFoodsAndBindToView() shows progress indication then hides it`() = runBlockingTest {
        setupGetAllFoodsUseCaseResult()
        excludeRecords { viewMvc.bindFoods(any()) }

        SUT.getAllFoodsAndBindToView()

        verifySequence {
            viewMvc.showProgressIndication()
            viewMvc.hideProgressIndication()
        }
    }

    @Test
    fun `onAddNewFoodClicked() goes to food form screen with new item id`() {
        SUT.onAddNewFoodClicked()

        verify { screensNavigator.toFoodFormScreen(Constants.NEW_ITEM_ID) }
    }

    @Test
    fun `onFoodDelete() - saves food id to savable data and shows prompt`() = runBlockingTest {
        val foodId = 1

        SUT.onFoodDelete(foodId)

        verify {
            savableData.setPendingIdOfFoodToDelete(foodId)
            dialogsManager.showDeleteFoodConfirmationDialog()
        }
    }

    @Test
    internal fun `onDialogEvent() - positive button clicked - deletes food with pending food id, invalidates pending food id, and refreshes foods`() = runBlockingTest {
        val foodId = Constants.EXISTING_ITEM_ID
        every { savableData.getPendingIdOfFoodToDelete() } returns foodId
        setupGetAllFoodsUseCaseResult()

        SUT.onDialogEvent(PromptDialogEvent.PositiveButtonClicked)

        coVerifyOrder {
            deleteFoodUseCase.deleteFood(foodId)
            savableData.setPendingIdOfFoodToDelete(-2)
            getAllFoodsUseCase.getAllFoods()
        }
    }

    @Test
    internal fun `onDialogEvent() - negative button clicked - do nothing`() {
        SUT.onDialogEvent(PromptDialogEvent.NegativeButtonClicked)

        verify {
            listOf(deleteFoodUseCase, savableData, getAllFoodsUseCase) wasNot Called
        }
    }

    @Test
    fun `onFoodEdit() opens food form screen with given id`() {
        val foodId = Constants.EXISTING_ITEM_ID

        SUT.onFoodEdit(foodId)

        verify { screensNavigator.toFoodFormScreen(foodId) }
    }

    fun setupGetAllFoodsUseCaseResult() {
        val useCaseResult = listOf(TestDefModels.defUiFood)
        coEvery { getAllFoodsUseCase.getAllFoods() } returns useCaseResult
    }
}