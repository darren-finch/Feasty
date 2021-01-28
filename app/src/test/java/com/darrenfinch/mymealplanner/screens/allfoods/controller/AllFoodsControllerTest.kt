package com.darrenfinch.mymealplanner.screens.allfoods.controller

import com.darrenfinch.mymealplanner.TestConstants
import com.darrenfinch.mymealplanner.TestDefaultModels
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.foods.usecases.DeleteFoodUseCase
import com.darrenfinch.mymealplanner.foods.usecases.GetAllFoodsUseCase
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
    private val deleteFoodUseCase = mockk<DeleteFoodUseCase>(relaxUnitFun = true)

    private val viewMvc = mockk<AllFoodsViewMvc>(relaxUnitFun = true)

    private lateinit var SUT: AllFoodsController

    @BeforeEach
    fun setUp() {
        SUT = AllFoodsController(
            screensNavigator,
            getAllFoodsUseCase,
            deleteFoodUseCase,
            coroutinesTestExtension.testDispatcher,
            coroutinesTestExtension.testDispatcher
        )
        SUT.bindView(viewMvc)
    }

    @Test
    fun `getAllFoodsAndBindToView() gets all foods using use case and binds them to view`() = runBlockingTest {
        val useCaseResult = listOf(TestDefaultModels.defUiFood)
        coEvery { getAllFoodsUseCase.getAllFoods() } returns useCaseResult

        SUT.getAllFoodsAndBindToView()

        coVerify { getAllFoodsUseCase.getAllFoods() }
        coVerify { viewMvc.bindFoods(useCaseResult) }
    }

    @Test
    fun `getAllFoodsAndBindToView() shows progress indication then hides it`() = runBlockingTest {
        setupGetAllFoodsUseCase()
        excludeRecords { viewMvc.bindFoods(any()) }

        SUT.getAllFoodsAndBindToView()

        verifySequence {
            viewMvc.showProgressIndication()
            viewMvc.hideProgressIndication()
        }
    }

    @Test
    fun `onAddNewFoodClicked() goes to food form screen with invalid id`() {
        SUT.onAddNewFoodClicked()

        verify { screensNavigator.toFoodFormScreen(TestConstants.INVALID_ID) }
    }

    @Test
    fun `onFoodDelete() deletes food with use case`() = runBlockingTest {
        val foodId = TestConstants.VALID_ID
        setupGetAllFoodsUseCase()

        SUT.onFoodDelete(foodId)

        coVerify { deleteFoodUseCase.deleteFood(foodId) }
    }

    @Test
    fun `onFoodEdit() opens food form screen with given id`() {
        val foodId = TestConstants.VALID_ID

        SUT.onFoodEdit(foodId)

        verify { screensNavigator.toFoodFormScreen(foodId) }
    }

    fun setupGetAllFoodsUseCase() {
        val useCaseResult = listOf(TestDefaultModels.defUiFood)
        coEvery { getAllFoodsUseCase.getAllFoods() } returns useCaseResult
    }
}