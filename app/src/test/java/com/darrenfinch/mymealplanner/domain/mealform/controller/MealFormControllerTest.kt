package com.darrenfinch.mymealplanner.domain.mealform.controller

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.darrenfinch.mymealplanner.InstantExecutorExtension
import com.darrenfinch.mymealplanner.TestData
import com.darrenfinch.mymealplanner.TestData.DEFAULT_VALID_MEAL_ID
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.domain.mealform.view.MealFormViewMvc
import com.darrenfinch.mymealplanner.domain.usecases.GetMealUseCase
import com.darrenfinch.mymealplanner.domain.usecases.InsertMealUseCase
import com.darrenfinch.mymealplanner.domain.usecases.UpdateMealUseCase
import com.darrenfinch.mymealplanner.model.data.entities.Meal
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
internal class MealFormControllerTest {
    //region Constants -----------------------------------------------------------------------------

    //endregion Constants --------------------------------------------------------------------------

    //region Helper Fields -------------------------------------------------------------------------
    private val viewLifecycleOwner = mockk<LifecycleOwner>()
    private val lifecycle = LifecycleRegistry(viewLifecycleOwner)

    private val defaultMealFood = TestData.defaultMealFood
    private val defaultMeal = TestData.defaultMeal
    private val defaultObservableMeal = ObservableMeal()

    private val insertMealUseCase = mockk<InsertMealUseCase>(relaxUnitFun = true)
    private val updateMealUseCase = mockk<UpdateMealUseCase>(relaxUnitFun = true)
    private val getMealUseCase = mockk<GetMealUseCase>(relaxUnitFun = true)
    private val screensNavigator = mockk<ScreensNavigator>(relaxUnitFun = true)

    private val viewMvc = mockk<MealFormViewMvc>(relaxUnitFun = true)
    //endregion Helper Fields ----------------------------------------------------------------------

    private lateinit var SUT: MealFormController

    //region Set up / Tear down
    @BeforeEach
    fun setUp() {
        SUT = MealFormController(
            insertMealUseCase,
            updateMealUseCase,
            getMealUseCase,
            screensNavigator,
        )
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
        SUT.fetchMealDetailsIfPossibleRebindToViewMvcOtherwise(viewLifecycleOwner)
        verify { viewMvc.bindMealDetails(defaultObservableMeal) }
    }

    @Test
    internal fun `onViewCreated() adds new meal food to current meal if not editing meal for the first time`() {
        // The controller is editing a meal for the first time if mealId == -1
        SUT.fetchMealDetailsIfPossibleRebindToViewMvcOtherwise(viewLifecycleOwner)
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
        SUT.onAddNewFoodButtonClicked()
        verify { screensNavigator.navigateToSelectFoodForMealScreen(defaultMeal) }
    }

    @Test
    internal fun `doneButtonClicked() navigates to all meals screen`() {
        SUT.onDoneButtonClicked()
        verify { screensNavigator.navigateToAllMealsScreen() }
    }

    @Test
    internal fun `doneButtonClicked() inserts meal with use case`() {
        SUT.onDoneButtonClicked()
        verify { insertMealUseCase.insertMeal(defaultMeal) }
    }

    @Test
    internal fun `doneButtonClicked() updates meal with use case when not editing meal for the first time`() {
        SUT = MealFormController(
            viewModel,
            insertMealUseCase,
            updateMealUseCase,
            getMealUseCase,
            screensNavigator,
            defaultMealFood,
            defaultMeal,
            DEFAULT_VALID_MEAL_ID
        )
        SUT.onDoneButtonClicked()
        verify { updateMealUseCase.updateMeal(defaultMeal) }
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