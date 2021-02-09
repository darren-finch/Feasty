package com.darrenfinch.mymealplanner.screens.allmeals.controller

import com.darrenfinch.mymealplanner.TestConstants
import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.meals.usecases.DeleteMealUseCase
import com.darrenfinch.mymealplanner.meals.usecases.GetAllMealsUseCase
import com.darrenfinch.mymealplanner.screens.allmeals.view.AllMealsViewMvc
import com.darrenfinch.mymealplanner.testrules.CoroutinesTestExtension
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@ExperimentalCoroutinesApi
internal class AllMealsControllerTest {
    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    private val screensNavigator = mockk<ScreensNavigator>(relaxUnitFun = true)
    private val getAllMealsUseCase = mockk<GetAllMealsUseCase>(relaxUnitFun = true)
    private val deleteMealUseCase = mockk<DeleteMealUseCase>(relaxUnitFun = true)

    private val viewMvc = mockk<AllMealsViewMvc>(relaxUnitFun = true)

    private lateinit var SUT: AllMealsController

    @BeforeEach
    fun setUp() {
        SUT = AllMealsController(
            screensNavigator,
            getAllMealsUseCase,
            deleteMealUseCase,
            coroutinesTestExtension.testDispatcher,
            coroutinesTestExtension.testDispatcher
        )
        SUT.bindView(viewMvc)
    }

    @Test
    fun `getAllMealsAndBindToView() gets all meals using use case and binds them to view`() = runBlockingTest {
        val useCaseResult = listOf(TestDefModels.defUiMeal)
        coEvery { getAllMealsUseCase.getAllMeals() } returns useCaseResult

        SUT.getAllMealsAndBindToView()

        coVerify { getAllMealsUseCase.getAllMeals() }
        coVerify { viewMvc.bindMeals(useCaseResult) }
    }

    @Test
    fun `getAllMealsAndBindToView() shows progress indication then hides it`() = runBlockingTest {
        setupGetAllMealsUseCase()
        excludeRecords { viewMvc.bindMeals(any()) }

        SUT.getAllMealsAndBindToView()

        verifySequence {
            viewMvc.showProgressIndication()
            viewMvc.hideProgressIndication()
        }
    }

    @Test
    fun `onAddNewMealClicked() goes to meal form screen with invalid id`() {
        SUT.onAddNewMealClicked()

        verify { screensNavigator.toMealFormScreen(TestConstants.INVALID_ID) }
    }

    @Test
    fun `onMealDelete() deletes meal with use case`() = runBlockingTest {
        val mealId = TestConstants.VALID_ID
        setupGetAllMealsUseCase()

        SUT.onMealDelete(mealId)

        coVerify { deleteMealUseCase.deleteMeal(mealId) }
    }

    @Test
    fun `onFoodEdit() opens meal form screen with given id`() {
        val mealId = TestConstants.VALID_ID

        SUT.onMealEdit(mealId)

        verify { screensNavigator.toMealFormScreen(mealId) }
    }

    fun setupGetAllMealsUseCase() {
        val useCaseResult = listOf(TestDefModels.defUiMeal)
        coEvery { getAllMealsUseCase.getAllMeals() } returns useCaseResult
    }
}