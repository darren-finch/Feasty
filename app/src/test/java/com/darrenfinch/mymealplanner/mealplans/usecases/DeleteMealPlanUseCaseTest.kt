package com.darrenfinch.mymealplanner.mealplans.usecases

import com.darrenfinch.mymealplanner.TestDefModels
import com.darrenfinch.mymealplanner.data.MainRepository
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class DeleteMealPlanUseCaseTest {

    private val mealPlanId = 1
    private val dbMealPlanMeal1 = TestDefModels.defDbMealPlanMeal.copy(id = 0, mealPlanId = mealPlanId, mealId = 0)
    private val dbMealPlanMeal2 = TestDefModels.defDbMealPlanMeal.copy(id = 1, mealPlanId = mealPlanId, mealId = 1)
    private val dbMealPlanMeals = listOf(dbMealPlanMeal1, dbMealPlanMeal2)

    private val repository = mockk<MainRepository>(relaxUnitFun = true)

    private lateinit var SUT: DeleteMealPlanUseCase

    @BeforeEach
    internal fun setUp() {
        SUT = DeleteMealPlanUseCase(
            repository
        )

        coEvery { repository.getMealsForMealPlan(mealPlanId) } returns dbMealPlanMeals
    }

    @Test
    internal fun `deleteMealPlanMeal() deletes all meals that the meal plan contained`() = runBlockingTest {
        SUT.deleteMealPlan(mealPlanId)

        coVerify {
            repository.deleteMealPlanMeal(dbMealPlanMeal1.id)
            repository.deleteMealPlanMeal(dbMealPlanMeal2.id)
        }
    }

    @Test
    internal fun `deleteMealPlanMeal() deletes meal plan meal from repository`() = runBlockingTest {
        SUT.deleteMealPlan(mealPlanId)

        coVerify {
            repository.deleteMealPlanMeal(mealPlanId)
        }
    }
}