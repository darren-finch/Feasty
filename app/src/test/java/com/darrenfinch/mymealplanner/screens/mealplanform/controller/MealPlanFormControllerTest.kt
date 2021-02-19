package com.darrenfinch.mymealplanner.screens.mealplanform.controller

import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.common.navigation.BackPressDispatcher
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.mealplans.usecases.InsertMealPlanUseCase
import com.darrenfinch.mymealplanner.screens.mealplanform.MealPlanFormData
import com.darrenfinch.mymealplanner.screens.mealplanform.view.MealPlanFormViewMvc
import com.darrenfinch.mymealplanner.testrules.CoroutinesTestExtension
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@ExperimentalCoroutinesApi
internal class MealPlanFormControllerTest {
    val uiMealPlanDetails = TestDefModels.defUiMealPlan.copy(title = "defUiMealPlan")

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    private val screenData = mockk<MealPlanFormData>(relaxUnitFun = true)
    private val screensNavigator = mockk<ScreensNavigator>(relaxUnitFun = true)
    private val backPressDispatcher = mockk<BackPressDispatcher>(relaxUnitFun = true)
    private val insertMealPlanUseCase = mockk<InsertMealPlanUseCase>(relaxUnitFun = true)

    private val viewMvc = mockk<MealPlanFormViewMvc>(relaxUnitFun = true)

    private lateinit var SUT: MealPlanFormController

    @BeforeEach
    fun setUp() {
        SUT = MealPlanFormController(
            screenData,
            insertMealPlanUseCase,
            screensNavigator,
            backPressDispatcher,
            coroutinesTestExtension.testDispatcher
        )
        SUT.bindView(viewMvc)

        every { screenData.getMealPlanDetails() } returns uiMealPlanDetails
        every { screensNavigator.navigateUp() } returns true
    }

    @Test
    internal fun `onStart() registers listeners`() {
        SUT.onStart()

        verify {
            viewMvc.registerListener(SUT)
            backPressDispatcher.registerListener(SUT)
        }
    }

    @Test
    internal fun `onStop() registers listeners`() {
        SUT.onStop()

        verify {
            viewMvc.unregisterListener(SUT)
            backPressDispatcher.unregisterListener(SUT)
        }
    }

    @Test
    internal fun `bindMealDetailsToView() binds meal details to view from screen data`() {
        SUT.bindMealDetailsToView()

        verify {
            viewMvc.bindMealPlanDetails(uiMealPlanDetails)
        }
    }

    @Test
    internal fun `onDoneClicked() inserts meal plan and navigates up`() {
        SUT.onDoneButtonClicked()

        coVerifySequence {
            insertMealPlanUseCase.insertMealPlan(uiMealPlanDetails)
            screensNavigator.navigateUp()
        }
    }

    @Test
    internal fun `onBackPressed() navigates up and returns result`() {
        assertEquals(true, SUT.onBackPressed())
        verify { screensNavigator.navigateUp() }
    }

    @Test
    internal fun `onNavigateUp() navigates up`() {
        SUT.onNavigateUp()

        verify { screensNavigator.navigateUp() }
    }
}