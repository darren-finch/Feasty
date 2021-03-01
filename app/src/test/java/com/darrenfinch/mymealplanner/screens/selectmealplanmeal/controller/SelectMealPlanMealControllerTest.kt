package com.darrenfinch.mymealplanner.screens.selectmealplanmeal.controller

import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.common.navigation.ScreenDataReturnBuffer
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.meals.usecases.GetAllMealsUseCase
import com.darrenfinch.mymealplanner.meals.usecases.GetMealsForQueryUseCase
import com.darrenfinch.mymealplanner.screens.selectmealplanmeal.SelectMealPlanMealSavableData
import com.darrenfinch.mymealplanner.screens.selectmealplanmeal.view.SelectMealPlanMealViewMvc
import com.darrenfinch.mymealplanner.testrules.CoroutinesTestExtension
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@ExperimentalCoroutinesApi
internal class SelectMealPlanMealControllerTest {

    private val getAllMealsUseCaseResult = listOf(
        TestDefModels.defUiMeal.copy(title = "defUiMeal1"),
        TestDefModels.defUiMeal.copy(title = "defUiMeal2")
    )

    private val savableData = mockk<SelectMealPlanMealSavableData>(relaxUnitFun = true)
    private val getMealsForQueryUseCase = mockk<GetMealsForQueryUseCase>(relaxUnitFun = true)
    private val screensNavigator = mockk<ScreensNavigator>(relaxUnitFun = true)
    private val screenDataReturnBuffer = mockk<ScreenDataReturnBuffer>(relaxUnitFun = true)
    private val viewMvc = mockk<SelectMealPlanMealViewMvc>(relaxUnitFun = true)

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    private lateinit var SUT: SelectMealPlanMealController

    @BeforeEach
    internal fun setUp() {
        SUT = SelectMealPlanMealController(
            savableData,
            getMealsForQueryUseCase,
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
    internal fun `getAllMeals() - shows then hides progress indication, gets meals from cur query and binds them to view mvc`() = runBlockingTest {
        val query = "asd"
        every { savableData.getCurQuery() } returns query
        coEvery { getMealsForQueryUseCase.getMealsForQuery(query) } returns getAllMealsUseCaseResult

        SUT.getAllMeals()

        verifyOrder {
            viewMvc.showProgressIndication()
            viewMvc.hideProgressIndication()
            viewMvc.bindMeals(getAllMealsUseCaseResult)
        }
    }

    @Test
    internal fun `onNavigateUp() navigates up`() {
        SUT.onNavigateUp()

        verify { screensNavigator.navigateUp() }
    }

    @Test
    internal fun `onMealChosen() puts chosen meal into screen data return buffer and navigates up`() {
        val chosenMeal = TestDefModels.defUiMeal.copy(title = "chosenMeal")

        SUT.onMealChosen(chosenMeal)

        verifySequence {
            screenDataReturnBuffer.putData(chosenMeal, SelectMealPlanMealFragment.ASYNC_COMPLETION_TOKEN)
            screensNavigator.navigateUp()
        }
    }

    @Test
    internal fun `onQuerySubmitted - saves submitted query and refreshes meal plan meals`() = runBlockingTest {
        val query = "asd"
        val uiMeals = listOf(TestDefModels.defUiMeal.copy(id = 0), TestDefModels.defUiMeal.copy(id = 1))
        coEvery { savableData.getCurQuery() } returns query
        coEvery { getMealsForQueryUseCase.getMealsForQuery(query) } returns uiMeals

        SUT.onQuerySubmitted(query)

        coVerify {
            savableData.setCurQuery(query)
            getMealsForQueryUseCase.getMealsForQuery(query)
        }
    }
}