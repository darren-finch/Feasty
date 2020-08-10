package com.darrenfinch.mymealplanner.domain.addeditmeal.controller

import com.darrenfinch.mymealplanner.TestData
import com.darrenfinch.mymealplanner.common.ScreensNavigator
import com.darrenfinch.mymealplanner.domain.addeditmeal.view.AddEditMealViewMvc
import com.darrenfinch.mymealplanner.domain.common.ObservableMeal
import com.darrenfinch.mymealplanner.domain.usecases.InsertMealUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class AddEditMealControllerTest {
    //region Constants -----------------------------------------------------------------------------

    //endregion Constants --------------------------------------------------------------------------

    //region Helper Fields -------------------------------------------------------------------------
    private val defaultMealFood = TestData.defaultMealFood
    private val defaultMeal = TestData.defaultMeal

    private val viewModel = mockk<AddEditMealViewModel>(relaxUnitFun = true)
    private val insertMealUseCase = mockk<InsertMealUseCase>(relaxUnitFun = true)
    private val screensNavigator = mockk<ScreensNavigator>(relaxUnitFun = true)
    private val viewMvc = mockk<AddEditMealViewMvc>(relaxUnitFun = true)
    //endregion Helper Fields ----------------------------------------------------------------------

    private lateinit var SUT: AddEditMealController

    //region Set up / Tear down
    @BeforeEach
    fun setUp() {
        SUT = AddEditMealController(viewModel, insertMealUseCase, screensNavigator, defaultMealFood)
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

//    @Test
//    internal fun `updateWithNewFoodData() adds new meal food to viewMvc and binds new meal food to viewModel`() {
//        SUT.updateWithNewFoodData()
//        verify { viewMvc.addNewMealFood() }
//        verify { viewModel.addNewMealFoodToMealData(defaultMealFood) }
//    }

//    @Test
//    internal fun `updateWithNewFoodData() doesn't interact with viewMvc or viewModel when newFoodData is null`() {
//        SUT = AddEditMealController(viewModel, insertMealUseCase, screensNavigator, null)
//        SUT.updateWithNewFoodData()
//        verify { viewMvc.addNewMealFood(allAny()) wasNot called }
//        verify { viewModel.addNewMealFoodToMealData(allAny()) wasNot called }
//    }

    @Test
    internal fun `bindMealDetails() binds meal details to viewMvc`() {
        val observableMeal = ObservableMeal()
        every { viewModel.getObservableMeal() } returns observableMeal
        SUT.bindMealDetails()
        verify { viewMvc.bindMealDetails(observableMeal) }
    }

    @Test
    internal fun `addNewFoodClicked() opens select food for meal screen and saves current meal data to viewModel`() {
        SUT.addNewFoodButtonClicked()
        verify { screensNavigator.navigateToSelectFoodForMealScreen(defaultMeal) }
    }

    @Test
    internal fun `doneButtonClicked() navigates to all meals screen`() {
        SUT.doneButtonClicked()
        verify { screensNavigator.navigateToAllMealsScreen() }
    }

    @Test
    internal fun `doneButtonClicked() inserts meal with use case`() {
        SUT.doneButtonClicked()
        verify { insertMealUseCase.insertMeal(defaultMeal) }
    }

    //endregion Tests ------------------------------------------------------------------------------

    //region Helper Classes ------------------------------------------------------------------------

    //endregion Helper Classes ---------------------------------------------------------------------

    //region Helper Methods ------------------------------------------------------------------------

    //endregion Helper Methods ---------------------------------------------------------------------
}