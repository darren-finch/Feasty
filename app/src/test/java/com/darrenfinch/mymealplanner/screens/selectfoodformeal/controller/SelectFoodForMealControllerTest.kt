package com.darrenfinch.mymealplanner.screens.selectfoodformeal.controller

import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.common.navigation.ScreenDataReturnBuffer
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.foods.usecases.GetAllFoodsUseCase
import com.darrenfinch.mymealplanner.screens.selectfoodformeal.view.SelectFoodForMealViewMvc
import com.darrenfinch.mymealplanner.testrules.CoroutinesTestExtension
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@ExperimentalCoroutinesApi
internal class SelectFoodForMealControllerTest {

    private val getAllFoodsUseCaseResult = listOf(
        TestDefModels.defUiFood.copy(title = "defUiFood1"),
        TestDefModels.defUiFood.copy(title = "defUiFood2")
    )

    private val getAllFoodsUseCase = mockk<GetAllFoodsUseCase>(relaxUnitFun = true)
    private val screensNavigator = mockk<ScreensNavigator>(relaxUnitFun = true)
    private val screenDataReturnBuffer = mockk<ScreenDataReturnBuffer>(relaxUnitFun = true)
    private val viewMvc = mockk<SelectFoodForMealViewMvc>(relaxUnitFun = true)

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    private lateinit var SUT: SelectFoodForMealController

    @BeforeEach
    internal fun setUp() {
        SUT = SelectFoodForMealController(
            getAllFoodsUseCase,
            screensNavigator,
            screenDataReturnBuffer,
            coroutinesTestExtension.testDispatcher,
            coroutinesTestExtension.testDispatcher
        )

        SUT.bindView(viewMvc)

        every { screensNavigator.navigateUp() } returns true
    }

    @Test
    internal fun `onStart() registers listeners`() {
        SUT.onStart()

        verify {
            viewMvc.registerListener(SUT)
        }
    }

    @Test
    internal fun `onStop() unregisters listeners`() {
        SUT.onStop()

        verify {
            viewMvc.unregisterListener(SUT)
        }
    }

    @Test
    internal fun `getAllFoods() gets foods from use case and binds them to view mvc`() {
        coEvery { getAllFoodsUseCase.getAllFoods() } returns getAllFoodsUseCaseResult

        SUT.getAllFoods()

        verify { viewMvc.bindFoods(getAllFoodsUseCaseResult) }
    }

    @Test
    internal fun `onNavigateUp() navigates up`() {
        SUT.onNavigateUp()

        verify { screensNavigator.navigateUp() }
    }

    @Test
    internal fun `onFoodChosen() puts chosen food into screen data return buffer then navigates back`() {
        val chosenFood = TestDefModels.defUiFood.copy(title = "chosenFood")

        SUT.onFoodChosen(chosenFood)

        verifySequence {
            screenDataReturnBuffer.putData(
                chosenFood,
                SelectFoodForMealFragment.ASYNC_COMPLETION_TOKEN
            )
            screensNavigator.navigateUp()
        }
    }
}