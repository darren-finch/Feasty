package com.darrenfinch.mymealplanner.screens.selectmealplanmeal.controller

import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.meals.usecases.GetAllMealsUseCase
import com.darrenfinch.mymealplanner.screens.selectmealplanmeal.view.SelectMealPlanMealViewMvc
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
internal class SelectMealPlanMealControllerTest {

    private val defUiMeal1 = TestDefModels.defUiMeal.copy(title = "defUiMeal1")
    private val defUiMeal2 = TestDefModels.defUiMeal.copy(title = "defUiMeal2")
    private val getAllMealsUseCaseResult = listOf(defUiMeal1, defUiMeal2)

    private val getAllMealsUseCase = mockk<GetAllMealsUseCase>(relaxUnitFun = true)
    private val screensNavigator = mockk<ScreensNavigator>(relaxUnitFun = true)
    private val viewMvc = mockk<SelectMealPlanMealViewMvc>(relaxUnitFun = true)

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    private lateinit var SUT: SelectMealPlanMealController

    @BeforeEach
    internal fun setUp() {
        SUT = SelectMealPlanMealController(
            getAllMealsUseCase,
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
    internal fun `getAllMeals() gets meals from use case and binds them to view mvc`() {
        coEvery { getAllMealsUseCase.getAllMeals() } returns getAllMealsUseCaseResult

        SUT.getAllMeals()

        verify { viewMvc.bindMeals(getAllMealsUseCaseResult) }
    }

    @Test
    internal fun `onNavigateUp() navigates up`() {
        SUT.onNavigateUp()

        verify { screensNavigator.navigateUp() }
    }
}