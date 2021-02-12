package com.darrenfinch.mymealplanner.mealplans.usecases

import com.darrenfinch.mymealplanner.data.MainRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class DeleteMealPlanMealUseCaseTest {
    private val repository = mockk<MainRepository>(relaxUnitFun = true)

    private lateinit var SUT: DeleteMealPlanMealUseCase

    @BeforeEach
    internal fun setUp() {
        SUT = DeleteMealPlanMealUseCase(
            repository
        )
    }

    @Test
    internal fun `deleteMealPlanMeal() deletes meal plan meal from repository`() = runBlockingTest {
        val mealPlanMealId = 1

        SUT.deleteMealPlanMeal(mealPlanMealId)

        coVerify { repository.deleteMealPlanMeal(mealPlanMealId) }
    }
}