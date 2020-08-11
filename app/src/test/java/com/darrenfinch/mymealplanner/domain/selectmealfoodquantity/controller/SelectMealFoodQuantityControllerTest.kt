package com.darrenfinch.mymealplanner.domain.selectmealfoodquantity.controller

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.darrenfinch.mymealplanner.InstantExecutorExtension
import com.darrenfinch.mymealplanner.TestData
import com.darrenfinch.mymealplanner.TestData.DEFAULT_VALID_FOOD_ID
import com.darrenfinch.mymealplanner.common.ScreensNavigator
import com.darrenfinch.mymealplanner.domain.selectmealfoodquantity.view.SelectMealFoodViewMvc
import com.darrenfinch.mymealplanner.domain.usecases.GetFoodUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
internal class SelectMealFoodQuantityControllerTest {

    //region Constants -----------------------------------------------------------------------------
    private val defaultFood = TestData.defaultFood
    private val defaultFoodLiveData = TestData.defaultFoodLiveData

    private val defaultMealFood = TestData.defaultMealFood
    private val defaultMeal = TestData.defaultMeal
    //endregion Constants --------------------------------------------------------------------------

    //region Helper Fields -------------------------------------------------------------------------
    private val viewLifecycleOwner = mockk<LifecycleOwner>()
    private val lifecycle = LifecycleRegistry(viewLifecycleOwner)

    private val screensNavigator = mockk<ScreensNavigator>(relaxUnitFun = true)
    private val getSingleFoodUseCase = mockk<GetFoodUseCase>(relaxUnitFun = true)
    private val viewMvc = mockk<SelectMealFoodViewMvc>(relaxUnitFun = true)
    //endregion Helper Fields ----------------------------------------------------------------------

    private lateinit var SUT: SelectMealFoodQuantityController

    //region Set up / Tear down
    @BeforeEach
    fun setUp() {
        SUT = SelectMealFoodQuantityController(
            screensNavigator,
            getSingleFoodUseCase,
            DEFAULT_VALID_FOOD_ID,
            defaultMeal
        )
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
    internal fun `fetchFood() binds data to viewMvc from use case`() {
        every { getSingleFoodUseCase.fetchFood(any()) } returns defaultFoodLiveData
        SUT.fetchFood(viewLifecycleOwner)
        verify { getSingleFoodUseCase.fetchFood(DEFAULT_VALID_FOOD_ID) }
        verify { viewMvc.bindFood(defaultFood) }
    }

    @Test
    internal fun `onMealFoodQuantityChosen() navigates to add edit meal screen`() {
        SUT.onMealFoodQuantityChosen(defaultMealFood)
        verify {
            screensNavigator.navigateFromSelectMealFoodQuantityScreenToAddEditMealScreen(
                defaultMealFood,
                defaultMeal
            )
        }
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