package com.darrenfinch.mymealplanner.domain.mealform.controller

import com.darrenfinch.mymealplanner.TestData
import com.darrenfinch.mymealplanner.TestData.DEFAULT_INVALID_MEAL_ID
import com.darrenfinch.mymealplanner.common.misc.ScreensNavigator
import com.darrenfinch.mymealplanner.domain.mealform.view.MealFormViewMvc
import com.darrenfinch.mymealplanner.domain.observables.ObservableMeal
import com.darrenfinch.mymealplanner.domain.usecases.InsertMealUseCase
import com.darrenfinch.mymealplanner.model.data.entities.Meal
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class MealFormControllerTest {
    //region Constants -----------------------------------------------------------------------------

    //endregion Constants --------------------------------------------------------------------------

    //region Helper Fields -------------------------------------------------------------------------
    private val defaultMealFood = TestData.defaultMealFood
    private val defaultMeal = TestData.defaultMeal

    private val defaultObservableMeal = ObservableMeal()

    private val viewModel = mockk<MealFormViewModel>(relaxUnitFun = true)
    private val insertMealUseCase = mockk<InsertMealUseCase>(relaxUnitFun = true)
    private val screensNavigator = mockk<ScreensNavigator>(relaxUnitFun = true)
    private val viewMvc = mockk<MealFormViewMvc>(relaxUnitFun = true)
    //endregion Helper Fields ----------------------------------------------------------------------

    private lateinit var SUT: MealFormController

    //region Set up / Tear down
    @BeforeEach
    fun setUp() {
        SUT = MealFormController(viewModel, insertMealUseCase, screensNavigator, defaultMealFood, defaultMeal)
        SUT.bindView(viewMvc)
        every { viewModel.getObservableMeal() } returns defaultObservableMeal
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
    internal fun `onViewCreated() binds observable meal to view if not editing meal for the first time`() {
        // The controller is editing a meal for the first time if mealId == -1
        SUT.onViewCreated(DEFAULT_INVALID_MEAL_ID)
        verify { viewMvc.bindMealDetails(defaultObservableMeal) }
    }

    @Test
    internal fun `onViewCreated() adds new meal food to current meal if not editing meal for the first time`() {
        // The controller is editing a meal for the first time if mealId == -1
        SUT.onViewCreated(DEFAULT_INVALID_MEAL_ID)
        val newMeal = Meal(
            defaultMeal.id,
            defaultMeal.title,
            defaultMeal.foods.toMutableList().apply {
                add(defaultMealFood)
            })
        verify { viewModel.setObservableMeal(newMeal) }
    }

    @Test
    internal fun `addNewFoodClicked() opens select food for meal screen`() {
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