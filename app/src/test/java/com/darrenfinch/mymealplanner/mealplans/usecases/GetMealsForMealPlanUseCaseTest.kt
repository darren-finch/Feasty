package com.darrenfinch.mymealplanner.mealplans.usecases

import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.data.MainRepository
import com.darrenfinch.mymealplanner.mealplans.models.mappers.mealPlanMealToDbMealPlanMeal
import com.darrenfinch.mymealplanner.mealplans.models.mappers.mealPlanMealToUiMealPlanMeal
import com.darrenfinch.mymealplanner.mealplans.models.mappers.uiMealPlanMealToMealPlanMeal
import com.darrenfinch.mymealplanner.meals.usecases.GetMealUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class GetMealsForMealPlanUseCaseTest {
    private val mealPlanId = 1
    private val uiMealPlanMeal1 = TestDefModels.defUiMealPlanMeal.copy(id = 0, mealPlanId = mealPlanId, mealId = 0)
    private val uiMealPlanMeal2 = TestDefModels.defUiMealPlanMeal.copy(id = 1, mealPlanId = mealPlanId, mealId = 1)
    private val dbUiMealPlanMeal1 = mealPlanMealToDbMealPlanMeal(uiMealPlanMealToMealPlanMeal(uiMealPlanMeal1))
    private val dbUiMealPlanMeal2 = mealPlanMealToDbMealPlanMeal(uiMealPlanMealToMealPlanMeal(uiMealPlanMeal2))

    private val getMealResult1 = TestDefModels.defUiMeal.copy(id = 0)
    private val getMealsForMealPlanResult = listOf(dbUiMealPlanMeal1, dbUiMealPlanMeal2)

    private val repository = mockk<MainRepository>(relaxUnitFun = true)
    private val getMealUseCase = mockk<GetMealUseCase>()

    private lateinit var SUT: GetMealsForMealPlanUseCase

    @BeforeEach
    internal fun setUp() {
        SUT = GetMealsForMealPlanUseCase(
            repository,
            getMealUseCase
        )

        coEvery { getMealUseCase.getMealNullable(uiMealPlanMeal1.id) } returns getMealResult1
        coEvery { getMealUseCase.getMealNullable(uiMealPlanMeal2.id) } returns null
        coEvery { repository.getMealsForMealPlan(mealPlanId) } returns getMealsForMealPlanResult
    }

    @Test
    internal fun `getMealsForMealPlan() returns valid meals for meal plan`() = runBlockingTest {
        val expectedUiMealPlanMeals = listOf(uiMealPlanMeal1)

        assertEquals(expectedUiMealPlanMeals, SUT.getMealsForMealPlan(mealPlanId))
    }
}