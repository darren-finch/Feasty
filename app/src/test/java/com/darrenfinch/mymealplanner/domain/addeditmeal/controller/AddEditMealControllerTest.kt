package com.darrenfinch.mymealplanner.domain.addeditmeal.controller

import com.darrenfinch.mymealplanner.common.ScreensNavigator
import com.darrenfinch.mymealplanner.domain.addeditmeal.view.AddEditMealViewMvc
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class AddEditMealControllerTest {
    //region Constants -----------------------------------------------------------------------------

    //endregion Constants --------------------------------------------------------------------------

    //region Helper Fields -------------------------------------------------------------------------
    private val screensNavigator = mockk<ScreensNavigator>(relaxUnitFun = true)
    private val viewMvc = mockk<AddEditMealViewMvc>(relaxUnitFun = true)
    //endregion Helper Fields ----------------------------------------------------------------------

    private lateinit var SUT: AddEditMealController

    //region Set up / Tear down
    @BeforeEach
    fun setUp() {
        SUT = AddEditMealController(screensNavigator)
        SUT.bindView(viewMvc)
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
    internal fun `addNewFoodClicked() opens select food for meal screen`() {
        SUT.addNewFoodClicked()
        verify { screensNavigator.navigateToSelectFoodForMealScreen() }
    }

    //endregion Tests ------------------------------------------------------------------------------

    //region Helper Classes ------------------------------------------------------------------------

    //endregion Helper Classes ---------------------------------------------------------------------

    //region Helper Methods ------------------------------------------------------------------------

    //endregion Helper Methods ---------------------------------------------------------------------
}