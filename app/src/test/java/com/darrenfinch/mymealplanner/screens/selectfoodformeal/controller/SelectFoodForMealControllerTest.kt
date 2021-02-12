package com.darrenfinch.mymealplanner.screens.selectfoodformeal.controller

import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.common.logs.getClassTag
import com.darrenfinch.mymealplanner.common.navigation.ScreenResult
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.foods.usecases.GetAllFoodsUseCase
import com.darrenfinch.mymealplanner.screens.selectfoodformeal.view.SelectFoodForMealViewMvc
import com.darrenfinch.mymealplanner.testrules.CoroutinesTestExtension
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@ExperimentalCoroutinesApi
internal class SelectFoodForMealControllerTest {

    private val defUiFood1 = TestDefModels.defUiFood.copy(title = "defUiFood1")
    private val defUiFood2 = TestDefModels.defUiFood.copy(title = "defUiFood2")
    private val getAllFoodsUseCaseResult = listOf(defUiFood1, defUiFood2)

    private val getAllFoodsUseCase = mockk<GetAllFoodsUseCase>(relaxUnitFun = true)
    private val screensNavigator = mockk<ScreensNavigator>(relaxUnitFun = true)
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
}