package com.darrenfinch.mymealplanner.domain.selectfoodformeal.controller

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.darrenfinch.mymealplanner.Constants
import com.darrenfinch.mymealplanner.InstantExecutorExtension
import com.darrenfinch.mymealplanner.TestData
import com.darrenfinch.mymealplanner.common.ScreensNavigator
import com.darrenfinch.mymealplanner.domain.selectfoodformeal.view.SelectFoodForMealViewMvc
import com.darrenfinch.mymealplanner.domain.usecases.GetAllFoodsUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
internal class SelectFoodForMealControllerTest {
    //region Constants -----------------------------------------------------------------------------
    private val defaultFoodDataList = TestData.defaultFoodDataList
    private val defaultFoodListLiveData = TestData.defaultFoodListLiveData
    //endregion Constants --------------------------------------------------------------------------

    //region Helper Fields -------------------------------------------------------------------------
    private val viewLifecycleOwner = mockk<LifecycleOwner>()
    private val lifecycle = LifecycleRegistry(viewLifecycleOwner)

    private val screensNavigator = mockk<ScreensNavigator>(relaxUnitFun = true)
    private val getAllFoodsUseCase = mockk<GetAllFoodsUseCase>()

    private val viewMvc = mockk<SelectFoodForMealViewMvc>(relaxUnitFun = true)
    //endregion Helper Fields ----------------------------------------------------------------------

    private lateinit var SUT: SelectFoodForMealController

    //region Set up / Tear down
    @BeforeEach
    fun setUp() {
        SUT = SelectFoodForMealController(screensNavigator, getAllFoodsUseCase)
        SUT.bindView(viewMvc)

        setupInstantLifecycleEventComponents()
    }
    //endregion Set up / Tear down

    //region Tests ---------------------------------------------------------------------------------
    @Test
    internal fun `onStart() subscribes to viewMvc`() {
        SUT.onStart()
        verify { viewMvc.registerListener(SUT) }
    }

    @Test
    internal fun `onStop() un-subscribes to viewMvc`() {
        SUT.onStop()
        verify { viewMvc.unregisterListener(SUT) }
    }

    @Test
    internal fun `fetchAllFoods() binds foods to viewMvc from use case`() {
        every { getAllFoodsUseCase.fetchAllFoods() } returns defaultFoodListLiveData
        SUT.fetchAllFoods(viewLifecycleOwner)
        verify { viewMvc.bindFoods(defaultFoodDataList) }
        verify { getAllFoodsUseCase.fetchAllFoods() }
    }

    @Test
    internal fun `onFoodChosen() navigates to select food quantity screen and passes correct foodId`() {
        SUT.onFoodChosen(Constants.DEFAULT_FOOD_ID)
        verify { screensNavigator.navigateToSelectFoodQuantityScreen(Constants.DEFAULT_FOOD_ID) }
    }

    //endregion Tests ------------------------------------------------------------------------------

    //region Helper Classes ------------------------------------------------------------------------

    //endregion Helper Classes ---------------------------------------------------------------------

    //region Helper Methods ------------------------------------------------------------------------
    private fun setupInstantLifecycleEventComponents() {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        every { viewLifecycleOwner.lifecycle } returns lifecycle
    }
    //endregion Helper Methods ---------------------------------------------------------------------
}